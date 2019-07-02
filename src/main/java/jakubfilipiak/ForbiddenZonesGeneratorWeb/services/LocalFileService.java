package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.TypeOfFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.LocalFileRepository;
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

    public LocalFileService(LocalFileRepository fileRepository,
                            ServletContext servletContext) {
        this.fileRepository = fileRepository;
        this.servletContext = servletContext;
        createContextDirectory();
    }

    private void createContextDirectory() {
        tmpStoragePath = servletContext.getRealPath("/uploads");
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
                return Optional.of(LocalFile
                        .builder()
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

    public Optional<LocalFile> createFileInStorageDir(String fileName) {
        String uniqueFileName = createUniqueName(fileName);
        Optional<String> pathName = createPathname(uniqueFileName);
        if (pathName.isPresent()) {
            new File(pathName.get());
            return Optional.of(LocalFile
                    .builder()
                    .originalName(fileName)
                    .uniqueName(uniqueFileName)
                    .pathName(pathName.get())
                    .build());
        }
        return Optional.empty();
    }

    private String createUniqueName(String originalFileName) {
        return UUID.randomUUID().toString() + originalFileName;
    }

    private Optional<String> createPathname(String uniqueFileName) {
        String directory;
        if (isFileTypeAllowed(uniqueFileName)) {
            directory = storagePath;
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

    public File downloadFile(String uniqueFileName) {
        return new File(storagePath + uniqueFileName);
    }

    public boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists())
            return file.delete();
        return false;
    }

















    public boolean fileExists(String pathname) {

        boolean exists = new File(pathname).exists();
        if (!exists) {
            LOGGER.log(Level.WARNING, "File does not exist");
        }
        return exists;
    }



    public boolean isFileTypeCorrect(String pathname, TypeOfFile expectedType) {

        TypeOfFile type = getFileType(pathname);
        boolean typeCorrect = type == expectedType;
        if (!typeCorrect)
            LOGGER.log(Level.WARNING, "Wrong file type");
        return true;
    }

    private BufferedInputStream createBufferedInputStream(String pathname) throws FileNotFoundException {

        return new BufferedInputStream(new FileInputStream(new File(pathname)));
    }

    public BufferedReader createBufferedReader(String pathname) throws FileNotFoundException {

        return new BufferedReader(
                new InputStreamReader(createBufferedInputStream(pathname)));
    }

    public BufferedImage createBufferedImage(String pathname) throws IOException {

        return ImageIO.read(createBufferedInputStream(pathname));
    }



}
