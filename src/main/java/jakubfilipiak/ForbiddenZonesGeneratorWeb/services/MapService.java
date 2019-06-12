package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.FileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.FileType;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.MapConfigSingleton;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Coordinates;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class MapService {

    private BufferedImage mapImage;

    public MapService() {
        setMapImage();
    }

    public Color getPixelColor(Coordinates coordinates) {

        int pixelX = coordinates.getPixelX();
        int pixelY = coordinates.getPixelY();

        return new Color(mapImage.getRGB(pixelX, pixelY));
    }

    public int calculatePixelX(PointOfTrack pointOfTrack) {

        float relativeX =
                pointOfTrack.getLongitude() - MapConfigSingleton.INSTANCE.getRelativeLongitudeZero();
        float pixelX =
                relativeX / MapConfigSingleton.INSTANCE.getLongitudeResolution() * mapImage.getWidth() - 1;
        return (int)pixelX;
    }

    public int calculatePixelY(PointOfTrack pointOfTrack) {

        float relativeY =
                MapConfigSingleton.INSTANCE.getRelativeLatitudeZero() - pointOfTrack.getLatitude();
        float pixelY =
                relativeY / MapConfigSingleton.INSTANCE.getLatitudeResolution() * mapImage.getHeight() - 1;
        return (int) pixelY;
    }

    private void setMapImage() {

        FileService fileService = new FileService();
        String pathname = MapConfigSingleton.INSTANCE.getFilePathname();

        if (fileService.fileExists(pathname)) {
            if (fileService.isFileTypeCorrect(pathname, FileType.PNG)) {
                try {
                    mapImage = fileService.createBufferedImage(pathname);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
