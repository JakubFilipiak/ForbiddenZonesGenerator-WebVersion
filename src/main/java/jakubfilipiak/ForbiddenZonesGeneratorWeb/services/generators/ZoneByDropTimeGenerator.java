package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;

import java.time.LocalTime;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 30.06.2019.
 */
public class ZoneByDropTimeGenerator {

    private boolean isTheFirstPoint = true;
    private boolean isSecondZoneStarted = false;

    private LocalTime possibleDepartureTimeOfTheSecondZone;

    private LocalTime dropStartTime;
    private LocalTime dropStopTime;

    private LocalTime entranceTime;
    private LocalTime departureTime;

    public ZoneByDropTimeGenerator(LocalTime dropStartTime, LocalTime dropStopTime) {
        this.dropStartTime = dropStartTime;
        this.dropStopTime = dropStopTime;
    }

    public void updatePointsBuffer(PointOfTrack pointOfTrack) {
        if (isTheFirstPoint) {
            entranceTime = pointOfTrack.getTime();
            departureTime = dropStartTime;
            isTheFirstPoint = false;
        } else if (!isSecondZoneStarted) {
            entranceTime = dropStopTime;
            isSecondZoneStarted = true;
        } else if (pointOfTrack.getTime().isAfter(dropStopTime)) {
            possibleDepartureTimeOfTheSecondZone = pointOfTrack.getTime();
        }
    }

    public Optional<ForbiddenZone> createZoneFromBuffer() {
        if (isBufferReady()) {
            ForbiddenZone forbiddenZone =
                    ForbiddenZone.fromTimestamps(entranceTime, departureTime);
            cleanBuffer();
            return Optional.of(forbiddenZone);
        }
        return Optional.empty();
    }

    public Optional<ForbiddenZone> createPossibleZoneFromRemainingData() {
        ForbiddenZone forbiddenZone;
        if (possibleDepartureTimeOfTheSecondZone != null) {
            departureTime = possibleDepartureTimeOfTheSecondZone;
            forbiddenZone = ForbiddenZone.fromTimestamps(entranceTime, departureTime);
            cleanBuffer();
            return Optional.of(forbiddenZone);
        }
        cleanBuffer();
        return Optional.empty();
    }

    private boolean isBufferReady() {
        return entranceTime != null && departureTime != null;
    }

    private void cleanBuffer() {
        entranceTime = null;
        departureTime = null;
    }
}
