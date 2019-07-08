package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers;

import lombok.*;

import java.time.LocalTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TurnOfTrack that = (TurnOfTrack) o;
        return Double.compare(that.absoluteEntranceAngle, absoluteEntranceAngle) == 0 &&
                Double.compare(that.absoluteDepartureAngle, absoluteDepartureAngle) == 0 &&
                Double.compare(that.angle, angle) == 0 &&
                entrancePoint.equals(that.entrancePoint) &&
                middlePoint.equals(that.middlePoint) &&
                departurePoint.equals(that.departurePoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entrancePoint, middlePoint, departurePoint, absoluteEntranceAngle, absoluteDepartureAngle, angle);
    }
}
