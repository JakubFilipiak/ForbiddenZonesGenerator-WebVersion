package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.crypto.CustomSecretKey;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.TypeOfFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.LocalFileRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.CryptographyUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jakub Filipiak on 13.06.2019.
 */
@Service
public class LocalFileService {

    private static final Logger LOGGER =
            Logger.getLogger(LocalFileService.class.getName());

    private LocalFileRepository fileRepository;
    private ServletContext servletContext;
    private String tmpStoragePath;
    private final String storagePath = "uploadedFiles/";

    private CustomSecretKeyService keyService;

    public LocalFileService(LocalFileRepository fileRepository,
                            ServletContext servletContext,
                            CustomSecretKeyService keyService) {
        this.fileRepository = fileRepository;
        this.servletContext = servletContext;
        this.keyService = keyService;
        createContextDirectory();
    }

    private void createContextDirectory() {
        tmpStoragePath = servletContext.getRealPath("/tmpFiles/");
        LOGGER.log(Level.INFO, tmpStoragePath);

        Path path = Paths.get(tmpStoragePath);
        if (!Files.exists(path))
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public Optional<LocalFile> uploadFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = createUniqueName(originalFileName);
        Optional<String> pathName = createPathname(uniqueFileName);
        if (pathName.isPresent()) {
            try {
                saveMultipartFile(file, pathName.get());
                return Optional.of(LocalFile.builder()
                        .originalName(originalFileName)
                        .uniqueName(uniqueFileName)
                        .pathName(pathName.get())
                        .build());
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public Optional<LocalFile> uploadEncryptedFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String uniqueFileName = createUniqueName(originalFileName);
        Optional<String> pathName = createPathname(uniqueFileName);
        if (pathName.isPresent()) {
            Optional<CustomSecretKey> aesKey = keyService.getAesKey();
            if (aesKey.isPresent()) {
                Optional<File> encryptedFile =
                        CryptographyUtils.encryptFile(aesKey.get().getKey(), file,
                                pathName.get());
                if (encryptedFile.isPresent()) {
                    return Optional.of(LocalFile.builder()
                            .originalName(originalFileName)
                            .uniqueName(uniqueFileName)
                            .pathName(pathName.get())
                            .build());
                }
            }
        }
        return Optional.empty();
    }

    public Optional<LocalFile> createFileInStorageDir(String fileName) {
        String uniqueFileName = createUniqueName(fileName);
        Optional<String> pathName = createPathname(uniqueFileName);
        if (pathName.isPresent()) {
            new File(pathName.get());
            return Optional.of(LocalFile.builder()
                    .originalName(fileName)
                    .uniqueName(uniqueFileName)
                    .pathName(pathName.get())
                    .build());
        }
        return Optional.empty();
    }

    public Optional<LocalFile> createFileInTmpDir(String fileName) {
        String uniqueFileName = createUniqueName(fileName);
        Optional<String> pathName = createTmpPathname(uniqueFileName);
        if (pathName.isPresent()) {
            new File(pathName.get());
            return Optional.of(LocalFile.builder()
                    .originalName(fileName)
                    .uniqueName(uniqueFileName)
                    .pathName(pathName.get())
                    .build());
        }
        return Optional.empty();
    }

    private String createUniqueName(String originalFileName) {
        int extensionStartIndex = originalFileName.lastIndexOf(".");
        String name = originalFileName.substring(0, extensionStartIndex);
        String extension = originalFileName.substring(extensionStartIndex);
        return name + UUID.randomUUID().toString() + extension;
    }

    private Optional<String> createPathname(String uniqueFileName) {
        String directory;
        if (isFileTypeAllowed(uniqueFileName)) {
            directory = storagePath;
            return Optional.of(directory + uniqueFileName);
        }
        return Optional.empty();
    }

    private Optional<String> createTmpPathname(String uniqueFileName) {
        String directory;
        if (isFileTypeAllowed(uniqueFileName)) {
            directory = tmpStoragePath;
            return Optional.of(directory + uniqueFileName);
        }
        return Optional.empty();
    }

    private boolean isFileTypeAllowed(String uniqueFileName) {
        TypeOfFile typeOfFile = getFileType(uniqueFileName);
        return typeOfFile == TypeOfFile.PNG
                || typeOfFile == TypeOfFile.TRK
                || typeOfFile == TypeOfFile.TXT;
    }

    private TypeOfFile getFileType(String filename) {
        String extension = filename.substring(filename.lastIndexOf("."));
        return TypeOfFile.fromString(extension);
    }

    private void saveMultipartFile(MultipartFile file, String pathName) throws IOException {
        InputStream fileStream = file.getInputStream();
        File targetFile = new File(pathName);
        FileUtils.copyInputStreamToFile(fileStream, targetFile);
    }

    public Optional<LocalFile> getLocalFileByUniqueName(String uniqueFileName) {
        return fileRepository.findByUniqueName(uniqueFileName);
    }

    public Optional<LocalFile> getLocalFileByUniquePathname(String uniquePathname) {
        return fileRepository.findByUniquePathname(uniquePathname);
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getTmpStoragePath() {
        return tmpStoragePath;
    }

    public File downloadFile(String uniqueFileName) {
        return new File(storagePath + uniqueFileName);
    }

    public boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists())
            return file.delete();
        return false;
    }
}
