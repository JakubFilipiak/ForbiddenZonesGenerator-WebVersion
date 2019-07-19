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

    private boolean zoneByDropTimeCreation;
    private boolean zoneByPointsCreation;
    private boolean zoneByTurnsCreation;

    private boolean verified;
    private boolean deleted;
}
