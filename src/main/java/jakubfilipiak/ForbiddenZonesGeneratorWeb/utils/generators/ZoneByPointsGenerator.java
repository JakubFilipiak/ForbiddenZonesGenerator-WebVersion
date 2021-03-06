package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsTimeConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.Coordinates;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.ColorChecker;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.NeighborhoodCreator;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.io.PngReader;

import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class ZoneByPointsGenerator {

    private ZoneByPointsConfig config;
    private ZoneByPointsTimeConfig timeConfig;
    private MapConfig mapConfig;

    private int minPointsNumberInSeries;
    private int maxPausesNumberBetweenPoints;
    private boolean isPointNeighborhoodVerification;
    private int radiusOfPixelsToBeVerified;

    private PointOfTrack entrancePoint;
    private PointOfTrack departurePoint;
    private int pointsCounter = 0;
    private int pauseCounter = 0;

    private ColorChecker colorChecker;
    private PngReader pngReader;
    private NeighborhoodCreator neighborhoodCreator;

    public ZoneByPointsGenerator(ZoneByPointsConfig config,
                                 ZoneByPointsTimeConfig timeConfig,
                                 MapConfig mapConfig) {
        this.config = config;
        this.timeConfig = timeConfig;
        this.mapConfig = mapConfig;
        initConfig();
    }

    private void initConfig() {
        this.minPointsNumberInSeries =
                config.getMinPointsNumberInSeries();
        this.maxPausesNumberBetweenPoints =
                config.getMaxPausesNumberBetweenPoints();
        this.isPointNeighborhoodVerification =
                config.isPointNeighborhoodVerification();
        if (isPointNeighborhoodVerification) {
            this.radiusOfPixelsToBeVerified = config.getRadiusOfPixelsToBeVerified();
        }

        colorChecker = new ColorChecker(mapConfig.getForbiddenRGBColor());
        pngReader = new PngReader(mapConfig);
        neighborhoodCreator = new NeighborhoodCreator();
    }

    public void updatePointsBuffer(PointOfTrack pointOfTrack) {
        if (isPointForbidden(pointOfTrack)) {
            if (!isForbiddenZoneStarted()) {
                entrancePoint = pointOfTrack;
                departurePoint = pointOfTrack;
                pauseCounter = 0;
            } else {
                departurePoint = pointOfTrack;
            }
            pointsCounter++;
        } else {
            pauseCounter++;
        }
    }

    public Optional<ForbiddenZone> createZoneFromBuffer() {
        if (isBufferReady()) {
            ForbiddenZone forbiddenZone;
            if (isOnlyOnePoint()) {
                forbiddenZone = ForbiddenZone.fromSinglePoint(entrancePoint, timeConfig);
            } else {
                forbiddenZone = ForbiddenZone.fromGroupOfPoints(entrancePoint,
                        departurePoint, timeConfig);
            }
            cleanBuffer();
            return Optional.of(forbiddenZone);
        }
        return Optional.empty();
    }

    public Optional<ForbiddenZone> createPossibleZoneFromRemainingData() {
        pngReader.close();
        boolean enoughPoints =
                pointsCounter >= minPointsNumberInSeries;
        if (enoughPoints) {
            ForbiddenZone forbiddenZone;
            if (isOnlyOnePoint()) {
                forbiddenZone = ForbiddenZone.fromSinglePoint(entrancePoint, timeConfig);
            } else {
                forbiddenZone = ForbiddenZone.fromGroupOfPoints(entrancePoint,
                        departurePoint, timeConfig);
            }
            cleanBuffer();
            return Optional.of(forbiddenZone);
        }
        cleanBuffer();
        return Optional.empty();
    }

    private boolean isBufferReady() {
        boolean enoughPoints =
                pointsCounter >= minPointsNumberInSeries;
        boolean tooManyPauses =
                pauseCounter > maxPausesNumberBetweenPoints;
        boolean bufferReady =
                enoughPoints && tooManyPauses;

        if (!enoughPoints) {
            if (tooManyPauses) {
                cleanBuffer();
                return false;
            }
            return false;
        }
        return bufferReady;
    }

    private boolean isPointForbidden(PointOfTrack pointOfTrack) {
        Coordinates pixelCoordinates = pngReader.getPixelCoordinates(pointOfTrack);
        Color pixelColor = pngReader.getPixelColor(pixelCoordinates);
        boolean pixelForbidden = colorChecker.isColorForbidden(pixelColor);

        if (isPointNeighborhoodVerification && pixelForbidden) {
            List<Coordinates> pixelNeighbors =
                    neighborhoodCreator.getPixelNeighbors(pixelCoordinates, radiusOfPixelsToBeVerified);
            for (Coordinates neighbor : pixelNeighbors) {
                Color neighborColor = pngReader.getPixelColor(neighbor);
                if (colorChecker.isColorForbidden(neighborColor))
                    return true;
            }
            return false;
        }
        return pixelForbidden;
    }

    private boolean isForbiddenZoneStarted() {
        return entrancePoint != null;
    }

    private boolean isOnlyOnePoint() {
        return pointsCounter == 1;
    }

    private void cleanBuffer() {
        entrancePoint = null;
        departurePoint = null;
        pointsCounter = 0;
        pauseCounter = 0;
    }
}
