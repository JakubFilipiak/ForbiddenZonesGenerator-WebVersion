package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos;

import lombok.*;

/**
 * Created by Jakub Filipiak on 18.06.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackDto {

    private String trackName;

    // ------- config -------

    private String mapConfigName;
    private String zoneByPointsConfigName;
    private String zoneByTurnsConfigName;
    private String processingConfigName;

    // ---- end of config ----

    private String originalTrackFileName;
    private String uniqueTrackFileName;

    private String dropStartTime;
    private String dropStopTime;

    private boolean verified;
    private boolean deleted;
    private boolean processed;

    private String originalOutputFileName;
    private String uniqueOutputFileName;
}
