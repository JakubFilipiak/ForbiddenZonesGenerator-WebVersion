package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Coordinates;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class PngReader {

    private MapConfig config;

    private FileInputStream mapStream;
    private BufferedImage mapImage;

    public PngReader(MapConfig config) {
        this.config = config;
        createBufferedImage();
    }

    private void createBufferedImage() {
        try {
            mapStream =  new FileInputStream(
                            new File(config.getMapFile().getPathName()));
            mapImage = ImageIO.read(mapStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color getPixelColor(Coordinates coordinates) {
        int pixelX = coordinates.getPixelX();
        int pixelY = coordinates.getPixelY();
        return new Color(mapImage.getRGB(pixelX, pixelY));
    }

    public Coordinates getPixelCoordinates(PointOfTrack point) {
        int pixelX = calculatePixelX(point);
        int pixelY = calculatePixelY(point);
        return new Coordinates(pixelX, pixelY);
    }

    private int calculatePixelX(PointOfTrack point) {
        double relativeX =
                point.getLongitude() - config.getRelativeLongitudeZero();
        double pixelX =
                relativeX / config.getLongitudeResolution() * mapImage.getWidth() - 1;
        return (int)pixelX;
    }

    private int calculatePixelY(PointOfTrack point) {
        double relativeY =
                config.getRelativeLatitudeZero() - point.getLatitude();
        double pixelY =
                relativeY / config.getLatitudeResolution() * mapImage.getHeight() - 1;
        return (int) pixelY;
    }

    public void close() {
        if (mapStream != null) {
            try {
                mapStream.close();
                mapStream = null;
                mapImage = null;
                System.gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
