package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.Properties;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.ColorService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.MapService;

import java.awt.*;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class ZoneByColorGenerator {

    private PointOfTrack entrancePoint;
    private PointOfTrack departurePoint;
    private int pointsCounter = 0;
    private int pauseCounter = 0;

    private MapService mapService = new MapService();
    private ColorService colorService = new ColorService();

    public boolean updatePointsBuffer(PointOfTrack pointOfTrack) {
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
        return isBufferReady();
    }

    public ForbiddenZone createZoneFromBuffer() {

        ForbiddenZone forbiddenZone;
        if (isOnlyOnePoint()) {
            forbiddenZone = ForbiddenZone.fromSinglePoint(entrancePoint);
        } else {
            forbiddenZone = ForbiddenZone.fromGroupOfPoints(entrancePoint,
                    departurePoint);
        }
        cleanBuffer();
        return forbiddenZone;
    }

    private boolean isBufferReady() {

        boolean enoughPoints =
                pointsCounter >= Properties.INSTANCE.getMinPointsNumberInSeries();
        boolean tooManyPauses =
                pauseCounter > Properties.INSTANCE.getMaxPauseOfPoints();
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

        Color pixelColor = mapService.getPixelColor(pointOfTrack);
        return colorService.isColorForbidden(pixelColor);
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
