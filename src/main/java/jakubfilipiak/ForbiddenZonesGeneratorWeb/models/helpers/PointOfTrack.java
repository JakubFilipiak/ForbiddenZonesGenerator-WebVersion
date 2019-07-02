package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
@Getter
@Builder
@ToString
public class PointOfTrack {

    private double latitude;
    private double longitude;
    private LocalTime time;
}
