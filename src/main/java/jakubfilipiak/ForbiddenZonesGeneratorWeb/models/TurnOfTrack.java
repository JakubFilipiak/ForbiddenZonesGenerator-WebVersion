package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.TurnService;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class TurnOfTrack {

    private PointOfTrack entrancePoint;
    private PointOfTrack middlePoint;
    private PointOfTrack departurePoint;
    private double absoluteEntranceAngle;
    private double absoluteDepartureAngle;
    private double angle;

    private TurnOfTrack(TurnOfTrackBuilder turnOfTrackBuilder) {
        this.entrancePoint = turnOfTrackBuilder.entrancePoint;
        this.middlePoint = turnOfTrackBuilder.middlePoint;
        this.departurePoint = turnOfTrackBuilder.departurePoint;

        this.absoluteEntranceAngle = TurnService.calculateAbsoluteAngleOfLine(turnOfTrackBuilder.entrancePoint, turnOfTrackBuilder.middlePoint);
        this.absoluteDepartureAngle = TurnService.calculateAbsoluteAngleOfLine(turnOfTrackBuilder.middlePoint, turnOfTrackBuilder.departurePoint);
        this.angle = TurnService.calculateTurnAngle(this.absoluteEntranceAngle,
                this.absoluteDepartureAngle);
    }

    public LocalTime getEntranceTime() {
        return entrancePoint.getTime();
    }

    public LocalTime getMiddleTime() {
        return middlePoint.getTime();
    }

    public LocalTime getDepartureTime() {
        return departurePoint.getTime();
    }

    public double getAbsoluteEntranceAngle() {
        return absoluteEntranceAngle;
    }

    public double getAbsoluteDepartureAngle() {
        return absoluteDepartureAngle;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        return "TurnOfTrack{" +
                "entrancePoint=" + entrancePoint +
                ", middlePoint=" + middlePoint +
                ", departurePoint=" + departurePoint +
                ", absoluteEntranceAngle=" + absoluteEntranceAngle +
                ", absoluteDepartureAngle=" + absoluteDepartureAngle +
                ", angle=" + angle +
                '}';
    }

    public static class TurnOfTrackBuilder {

        private PointOfTrack entrancePoint;
        private PointOfTrack middlePoint;
        private PointOfTrack departurePoint;

        public TurnOfTrackBuilder setEntrancePoint(PointOfTrack entrancePoint) {
            this.entrancePoint = entrancePoint;
            return this;
        }

        public TurnOfTrackBuilder setMiddlePoint(PointOfTrack middlePoint) {
            this.middlePoint = middlePoint;
            return this;
        }

        public TurnOfTrackBuilder setDeparturePoint(PointOfTrack departurePoint) {
            this.departurePoint = departurePoint;
            return this;
        }

        public TurnOfTrack build() {
            return new TurnOfTrack(this);
        }
    }
}
