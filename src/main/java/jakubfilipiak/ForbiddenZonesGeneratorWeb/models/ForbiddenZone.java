package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.Properties;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class ForbiddenZone {

    private LocalTime entranceTime;
    private LocalTime departureTime;

    public ForbiddenZone(LocalTime entranceTime, LocalTime departureTime) {
        this.entranceTime = entranceTime;
        this.departureTime = departureTime;
    }

    public static ForbiddenZone fromSingleTurn(TurnOfTrack turnOfTrack) {
        Properties properties = Properties.INSTANCE;

        if (properties.isSingleTurnZoneFullTime()) {
            LocalTime entranceTime = turnOfTrack.getEntranceTime();
            LocalTime departureTime = turnOfTrack.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = turnOfTrack
                        .getMiddleTime()
                        .minusSeconds(properties.getSingleTurnZoneBeginOffset());
        LocalTime departureTime = turnOfTrack
                        .getMiddleTime()
                        .plusSeconds(properties.getSingleTurnZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfTurns(TurnOfTrack entranceTurn,
                                                 TurnOfTrack departureTurn) {
        Properties properties = Properties.INSTANCE;

        if (properties.isGroupOfTurnsZoneFullTime()) {
            LocalTime entranceTime = entranceTurn.getEntranceTime();
            LocalTime departureTime = departureTurn.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = entranceTurn
                .getMiddleTime()
                .minusSeconds(properties.getGroupOfTurnsZoneBeginOffset());
        LocalTime departureTime = departureTurn
                .getMiddleTime()
                .plusSeconds(properties.getGroupOfTurnsZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromSinglePoint(PointOfTrack pointOfTrack) {
        Properties properties = Properties.INSTANCE;

        // TODO
        return null;
    }

    public static ForbiddenZone fromGroupOfPoints(PointOfTrack entrancePoint, PointOfTrack departurePoint) {

        // TODO
        return null;
    }

    @Override
    public String toString() {
        return "ForbiddenZone{" +
                "entranceTime=" + entranceTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
