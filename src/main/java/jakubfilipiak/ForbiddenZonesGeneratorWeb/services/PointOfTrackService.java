package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class PointOfTrackService {

    public boolean isPointOfTrackCorrect(PointOfTrack pointOfTrack) {

        boolean latitudeCorrect = pointOfTrack.getLatitude() != 0f;
        boolean longitudeCorrect = pointOfTrack.getLongitude() != 0f;
        boolean timeCorrect = !pointOfTrack.getTime().equals(LocalTime.of(0, 0, 0));

        return latitudeCorrect && longitudeCorrect && timeCorrect;
    }
}
