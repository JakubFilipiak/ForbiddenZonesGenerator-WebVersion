package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.ProcessingConfigSingleton;

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
        ProcessingConfigSingleton processingConfig = ProcessingConfigSingleton.INSTANCE;

        if (processingConfig.isSingleTurnZoneFullTime()) {
            LocalTime entranceTime = turnOfTrack.getEntranceTime();
            LocalTime departureTime = turnOfTrack.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = turnOfTrack
                        .getMiddleTime()
                        .minusSeconds(processingConfig.getSingleTurnZoneBeginOffset());
        LocalTime departureTime = turnOfTrack
                        .getMiddleTime()
                        .plusSeconds(processingConfig.getSingleTurnZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfTurns(TurnOfTrack entranceTurn,
                                                 TurnOfTrack departureTurn) {
        ProcessingConfigSingleton processingConfig = ProcessingConfigSingleton.INSTANCE;

        if (processingConfig.isGroupOfTurnsZoneFullTime()) {
            LocalTime entranceTime = entranceTurn.getEntranceTime();
            LocalTime departureTime = departureTurn.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = entranceTurn
                .getMiddleTime()
                .minusSeconds(processingConfig.getGroupOfTurnsZoneBeginOffset());
        LocalTime departureTime = departureTurn
                .getMiddleTime()
                .plusSeconds(processingConfig.getGroupOfTurnsZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromSinglePoint(PointOfTrack pointOfTrack) {
        ProcessingConfigSingleton processingConfig = ProcessingConfigSingleton.INSTANCE;
        LocalTime entranceTime = pointOfTrack
                .getTime()
                .minusSeconds(processingConfig.getSinglePointZoneBeginOffset());
        LocalTime departureTime = pointOfTrack
                .getTime()
                .plusSeconds(processingConfig.getSinglePointZoneEndOffset());

        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfPoints(PointOfTrack entrancePoint, PointOfTrack departurePoint) {
        ProcessingConfigSingleton processingConfig = ProcessingConfigSingleton.INSTANCE;
        LocalTime entranceTime = entrancePoint
                .getTime()
                .minusSeconds(processingConfig.getGroupOfPointsZoneBeginOffset());
        LocalTime departureTime = departurePoint
                .getTime()
                .plusSeconds(processingConfig.getGroupOfPointsZoneEndOffset());

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
