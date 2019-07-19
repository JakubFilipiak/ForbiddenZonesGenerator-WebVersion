package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos;

import lombok.*;

/**
 * Created by Jakub Filipiak on 19.06.2019.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneByTurnsConfigDto {

    private String configName;

    private int minTurnInitiationAngle;

    private boolean ignoreTurns;
    private int ignoredTurnMinValue;
    private int ignoredTurnMaxValue;

    private int minTurnsNumberInSeries;
    private int maxPausesNumberBetweenTurns;

    private boolean verified;
    private boolean deleted;
}
