package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.FileType;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
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

    private ServletContext servletContext;
    private String tmpUploadsPath;
    private final String uploadsPath = "uploadedMapFiles/";

    public LocalFileService(ServletContext servletContext) {
        this.servletContext = servletContext;
        createContextDirectory();
    }

    private void createContextDirectory() {
        tmpUploadsPath = servletContext.getRealPath("/uploads");
        LOGGER.log(Level.INFO, tmpUploadsPath);

        Path path = Paths.get(tmpUploadsPath);
        if (!Files.exists(path))
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public Optional<LocalFile> uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        Optional<String> uniquePathname = createUniquePathname(originalFilename);
        if (uniquePathname.isPresent()) {
            try {
                saveFile(file, uniquePathname.get());
                return Optional.of(
                        new LocalFile(originalFilename, uniquePathname.get()));
            } catch (IOException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private Optional<String> createUniquePathname(String originalFilename) {
        FileType fileType = getFileType(originalFilename);
        String directory;
        switch (fileType) {
            case PNG:
                directory = uploadsPath;
                break;
            case TRK:
                directory = tmpUploadsPath;
                break;
            default:
                return Optional.empty();
        }
        return Optional.of(
                directory + UUID.randomUUID().toString() + originalFilename);
    }

    private FileType getFileType(String filename) {
        String extension = filename.substring(filename.lastIndexOf("."));
        return FileType.fromString(extension);
    }

    private void saveFile(MultipartFile file, String pathname) throws IOException {
        InputStream fileStream = file.getInputStream();
        File targetFile = new File(pathname);
        FileUtils.copyInputStreamToFile(fileStream, targetFile);
    }

    public File downloadFile(String pathname) {
        return new File(pathname);
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



    public boolean isFileTypeCorrect(String pathname, FileType expectedType) {

        FileType type = getFileType(pathname);
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
