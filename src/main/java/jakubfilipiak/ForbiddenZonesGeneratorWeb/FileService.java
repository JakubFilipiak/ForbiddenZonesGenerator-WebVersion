package jakubfilipiak.ForbiddenZonesGeneratorWeb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class FileService {

    private static final Logger LOGGER = Logger.getLogger(FileService.class.getName());

    public boolean fileExists(String pathname) {

        boolean exists = new File(pathname).exists();
        if (!exists) {
            LOGGER.log(Level.WARNING, "File does not exist");
        }
        return exists;
    }

    private FileType getFileType(String pathname) {

        String extension = pathname.substring(pathname.lastIndexOf("."));
        return FileType.fromString(extension);
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
