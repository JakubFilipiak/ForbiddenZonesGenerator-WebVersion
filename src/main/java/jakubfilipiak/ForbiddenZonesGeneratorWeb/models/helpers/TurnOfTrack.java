package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers;

import lombok.*;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnOfTrack {

    private PointOfTrack entrancePoint;
    private PointOfTrack middlePoint;
    private PointOfTrack departurePoint;
    private double absoluteEntranceAngle;
    private double absoluteDepartureAngle;
    private double angle;

    public LocalTime getEntranceTime() {
        return entrancePoint.getTime();
    }

    public LocalTime getMiddleTime() {
        return middlePoint.getTime();
    }

    public LocalTime getDepartureTime() {
        return departurePoint.getTime();
    }
}
