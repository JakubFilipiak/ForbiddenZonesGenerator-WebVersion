package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.TurnOfTrack;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class TurnOfTrackGenerator {

    private PointOfTrack entrancePoint;
    private PointOfTrack middlePoint;
    private PointOfTrack departurePoint;

    public boolean updatePointsBuffer(PointOfTrack pointOfTrack) {

        entrancePoint = middlePoint;
        middlePoint = departurePoint;
        departurePoint = pointOfTrack;

        return isBufferReady();
    }

    private boolean isBufferReady() {

        boolean entrancePointReady = entrancePoint != null;
        boolean middlePointReady = middlePoint != null;
        boolean departurePointReady = departurePoint != null;

        return entrancePointReady && middlePointReady && departurePointReady;
    }

    public TurnOfTrack createTurnFromBuffer() {

        return new TurnOfTrack.TurnOfTrackBuilder()
                .setEntrancePoint(entrancePoint)
                .setMiddlePoint(middlePoint)
                .setDeparturePoint(departurePoint)
                .build();
    }
}
