package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos;

import lombok.*;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingConfigDto {

    private String configName;

    // types of zones to be created

    private boolean zoneByDropTimeCreation;
    private boolean zoneByPointsCreation;
    private boolean zoneByTurnsCreation;

    // properties

    private boolean verified;
    private boolean deleted;
}
