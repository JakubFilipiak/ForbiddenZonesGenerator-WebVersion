package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.ProcessingProperties;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class ForbiddenZone {

    private LocalTime entranceTime;
    private LocalTime departureTime;

    private ForbiddenZone(LocalTime entranceTime, LocalTime departureTime) {
        this.entranceTime = entranceTime;
        this.departureTime = departureTime;
    }

    public static ForbiddenZone fromSingleTurn(TurnOfTrack turnOfTrack) {
        ProcessingProperties processingProperties = ProcessingProperties.INSTANCE;

        if (processingProperties.isSingleTurnZoneFullTime()) {
            LocalTime entranceTime = turnOfTrack.getEntranceTime();
            LocalTime departureTime = turnOfTrack.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = turnOfTrack
                        .getMiddleTime()
                        .minusSeconds(processingProperties.getSingleTurnZoneBeginOffset());
        LocalTime departureTime = turnOfTrack
                        .getMiddleTime()
                        .plusSeconds(processingProperties.getSingleTurnZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfTurns(TurnOfTrack entranceTurn,
                                                 TurnOfTrack departureTurn) {
        ProcessingProperties processingProperties = ProcessingProperties.INSTANCE;

        if (processingProperties.isGroupOfTurnsZoneFullTime()) {
            LocalTime entranceTime = entranceTurn.getEntranceTime();
            LocalTime departureTime = departureTurn.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = entranceTurn
                .getMiddleTime()
                .minusSeconds(processingProperties.getGroupOfTurnsZoneBeginOffset());
        LocalTime departureTime = departureTurn
                .getMiddleTime()
                .plusSeconds(processingProperties.getGroupOfTurnsZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromSinglePoint(PointOfTrack pointOfTrack) {
        ProcessingProperties processingProperties = ProcessingProperties.INSTANCE;
        LocalTime entranceTime = pointOfTrack
                .getTime()
                .minusSeconds(processingProperties.getSinglePointZoneBeginOffset());
        LocalTime departureTime = pointOfTrack
                .getTime()
                .plusSeconds(processingProperties.getSinglePointZoneEndOffset());

        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfPoints(PointOfTrack entrancePoint, PointOfTrack departurePoint) {
        ProcessingProperties processingProperties = ProcessingProperties.INSTANCE;
        LocalTime entranceTime = entrancePoint
                .getTime()
                .minusSeconds(processingProperties.getGroupOfPointsZoneBeginOffset());
        LocalTime departureTime = departurePoint
                .getTime()
                .plusSeconds(processingProperties.getGroupOfPointsZoneEndOffset());

        return new ForbiddenZone(entranceTime, departureTime);
    }

    @Override
    public String toString() {
        return "ForbiddenZone{" +
                "entranceTime=" + entranceTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
