package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TurnOfTrack;

import java.util.Optional;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class TurnOfTrackGenerator {

    private PointOfTrack entrancePoint;
    private PointOfTrack middlePoint;
    private PointOfTrack departurePoint;

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
        if (isBufferReady())
        return Optional.of(new TurnOfTrack.TurnOfTrackBuilder()
                .setEntrancePoint(entrancePoint)
                .setMiddlePoint(middlePoint)
                .setDeparturePoint(departurePoint)
                .build());
        return Optional.empty();
    }
}
