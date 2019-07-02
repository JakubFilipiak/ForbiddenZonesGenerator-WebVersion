package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;
import lombok.Getter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class ForbiddenZone implements Comparable<ForbiddenZone>{

    @Getter
    private LocalTime entranceTime;
    @Getter
    private LocalTime departureTime;
    public static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private ForbiddenZone(LocalTime entranceTime, LocalTime departureTime) {
        this.entranceTime = entranceTime;
        this.departureTime = departureTime;
    }

    public static ForbiddenZone fromSingleTurn(TurnOfTrack turnOfTrack,
                                               ZoneByTurnsConfig config) {
        if (config.isSingleTurnZoneFullTime()) {
            LocalTime entranceTime = turnOfTrack.getEntranceTime();
            LocalTime departureTime = turnOfTrack.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = turnOfTrack
                        .getMiddleTime()
                        .minusSeconds(config.getSingleTurnZoneBeginOffset());
        LocalTime departureTime = turnOfTrack
                        .getMiddleTime()
                        .plusSeconds(config.getSingleTurnZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfTurns(TurnOfTrack entranceTurn,
                                                 TurnOfTrack departureTurn,
                                                 ZoneByTurnsConfig config) {
        if (config.isGroupOfTurnsZoneFullTime()) {
            LocalTime entranceTime = entranceTurn.getEntranceTime();
            LocalTime departureTime = departureTurn.getDepartureTime();
            return new ForbiddenZone(entranceTime, departureTime);
        }
        LocalTime entranceTime = entranceTurn
                .getMiddleTime()
                .minusSeconds(config.getGroupOfTurnsZoneBeginOffset());
        LocalTime departureTime = departureTurn
                .getMiddleTime()
                .plusSeconds(config.getGroupOfTurnsZoneEndOffset());
        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromSinglePoint(PointOfTrack pointOfTrack,
                                                ZoneByPointsConfig config) {
        LocalTime entranceTime = pointOfTrack
                .getTime()
                .minusSeconds(config.getSinglePointZoneBeginOffset());
        LocalTime departureTime = pointOfTrack
                .getTime()
                .plusSeconds(config.getSinglePointZoneEndOffset());

        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromGroupOfPoints(PointOfTrack entrancePoint,
                                                  PointOfTrack departurePoint,
                                                  ZoneByPointsConfig config) {
        LocalTime entranceTime = entrancePoint
                .getTime()
                .minusSeconds(config.getGroupOfPointsZoneBeginOffset());
        LocalTime departureTime = departurePoint
                .getTime()
                .plusSeconds(config.getGroupOfPointsZoneEndOffset());

        return new ForbiddenZone(entranceTime, departureTime);
    }

    public static ForbiddenZone fromTimestamps(LocalTime entranceTime, LocalTime departureTime) {
        return new ForbiddenZone(entranceTime, departureTime);
    }

    @Override
    public String toString() {
        String entranceTimeString = entranceTime.format(formatter);
        String departureTimeString = departureTime.format(formatter);
        return entranceTimeString + " " + departureTimeString;
    }

    @Override
    public int compareTo(ForbiddenZone o) {
        if (this.entranceTime.isBefore(o.getEntranceTime())) {
            return -1;
        } else if (this.entranceTime.isAfter(o.getEntranceTime())) {
            return 1;
        } else {
            if (this.departureTime.isBefore(o.getDepartureTime())) {
                return -1;
            } else if (this.departureTime.isAfter(o.getDepartureTime())) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
