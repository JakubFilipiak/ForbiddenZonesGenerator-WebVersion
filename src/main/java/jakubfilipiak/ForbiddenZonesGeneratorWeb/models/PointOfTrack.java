package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class PointOfTrack {

    private float latitude;
    private float longitude;
    private LocalTime time;

    private PointOfTrack(PointOfTrackBuilder pointOfTrackBuilder) {
        this.latitude = pointOfTrackBuilder.latitude;
        this.longitude = pointOfTrackBuilder.longitude;
        this.time = pointOfTrackBuilder.time;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "PointOfTrack{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", time=" + time +
                '}';
    }

    public static class PointOfTrackBuilder {

        private float latitude;
        private float longitude;
        private LocalTime time;

        public PointOfTrackBuilder setLatitude(float latitude) {
            this.latitude = latitude;
            return this;
        }

        public PointOfTrackBuilder setLongitude(float longitude) {
            this.longitude = longitude;
            return this;
        }

        public PointOfTrackBuilder setTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public PointOfTrack build() {
            return new PointOfTrack(this);
        }
    }
}
