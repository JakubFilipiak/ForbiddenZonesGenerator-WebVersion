package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 28.06.2019.
 */
public class MultipliedPointsGenerator {

    private PointOfTrack previousPoint;
    private PointOfTrack actualPoint;

    private int targetPointsPeriod = 1;

    private List<PointOfTrack> multipliedPoints = new ArrayList<>();

    public void updateBuffer(PointOfTrack pointOfTrack) {
        multipliedPoints.clear();
        updatePoints(pointOfTrack);
        if (isTheFirstPoint()) {
            multipliedPoints.add(actualPoint);
        } else {
            multipliedPoints.addAll(createMultipliedPoints());
        }
    }

    public List<PointOfTrack> getMultipliedPoints() {
        return multipliedPoints;
    }

    private void updatePoints(PointOfTrack pointOfTrack) {
        previousPoint = actualPoint;
        actualPoint = pointOfTrack;
    }

    private boolean isTheFirstPoint() {
        return previousPoint == null && actualPoint != null;
    }

    private List<PointOfTrack> createMultipliedPoints() {
        int divider = calculateDivider();
        double latitudeInterval = calculateLatitudeInterval(divider);
        double longitudeInterval = calculateLongitudeInterval(divider);

        return createSectionMultipliedPoints(
                latitudeInterval,
                longitudeInterval,
                divider);
    }

    private int calculateDivider() {
        int timeDelta = calculateTimeDelta();
        return timeDelta / targetPointsPeriod;
    }

    private int calculateTimeDelta() {
        LocalTime beginTime = previousPoint.getTime();
        LocalTime endTime = actualPoint.getTime();
        return (int)ChronoUnit.SECONDS.between(beginTime, endTime);
    }

    private double calculateLatitudeInterval(int divider) {
        return calculateLatitudeDelta() / divider;
    }

    private double calculateLatitudeDelta() {
        double beginLatitude = previousPoint.getLatitude();
        double endLatitude = actualPoint.getLatitude();
        return endLatitude - beginLatitude;
    }

    private double calculateLongitudeInterval(int divider) {
        return calculateLongitudeDelta() / divider;
    }

    private double calculateLongitudeDelta() {
        double beginLongitude = previousPoint.getLongitude();
        double endLongitude = actualPoint.getLongitude();
        return endLongitude - beginLongitude;
    }

    private List<PointOfTrack> createSectionMultipliedPoints(
            double latitudeInterval,
            double longitudeInterval,
            int divider) {
        List<PointOfTrack> sectionMultipliedPoints = new ArrayList<>();
        for (int i = 1; i < divider; i++) {
            double tmpLatitude =
                    previousPoint.getLatitude() + (i * latitudeInterval);
            double tmpLongitude =
                    previousPoint.getLongitude() + (i * longitudeInterval);
            LocalTime tmpTime =
                    previousPoint.getTime().plusSeconds(i * targetPointsPeriod);

            sectionMultipliedPoints.add(PointOfTrack
                    .builder()
                    .latitude(tmpLatitude)
                    .longitude(tmpLongitude)
                    .time(tmpTime)
                    .build());
        }
        sectionMultipliedPoints.add(actualPoint);
        return sectionMultipliedPoints;
    }
}
