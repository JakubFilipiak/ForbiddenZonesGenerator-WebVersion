package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TurnOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.TurnAngleCalculator;

import java.util.Optional;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class TurnOfTrackGenerator {

    private TurnAngleCalculator turnAngleCalculator;

    private PointOfTrack entrancePoint;
    private PointOfTrack middlePoint;
    private PointOfTrack departurePoint;

    public TurnOfTrackGenerator(TurnAngleCalculator turnAngleCalculator) {
        this.turnAngleCalculator = turnAngleCalculator;
    }

    public void updatePointsBuffer(PointOfTrack pointOfTrack) {
        entrancePoint = middlePoint;
        middlePoint = departurePoint;
        departurePoint = pointOfTrack;
    }

    private boolean isBufferReady() {
        boolean entrancePointReady = entrancePoint != null;
        boolean middlePointReady = middlePoint != null;
        boolean departurePointReady = departurePoint != null;
        return entrancePointReady && middlePointReady && departurePointReady;
    }

    public Optional<TurnOfTrack> createTurnFromBuffer() {
        if (isBufferReady()) {
            double absoluteEntranceAngle =
                    turnAngleCalculator.calculateAbsoluteAngleOfLine(
                            entrancePoint, middlePoint);
            double absoluteDepartureAngle =
                    turnAngleCalculator.calculateAbsoluteAngleOfLine(
                            middlePoint, departurePoint);
            double angle =
                    turnAngleCalculator.calculateTurnAngle(
                            absoluteEntranceAngle, absoluteDepartureAngle);

            return Optional.of(TurnOfTrack.builder()
                    .entrancePoint(entrancePoint)
                    .middlePoint(middlePoint)
                    .departurePoint(departurePoint)
                    .absoluteEntranceAngle(absoluteEntranceAngle)
                    .absoluteDepartureAngle(absoluteDepartureAngle)
                    .angle(angle)
                    .build());
        }
        return Optional.empty();
    }
}
