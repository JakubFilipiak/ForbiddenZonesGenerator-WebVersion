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

    // turn

    private int minTurnInitiationAngle;

    private boolean ignoreTurns;

    private int ignoredTurnMinValue;
    private int ignoredTurnMaxValue;

    // zone forbidden by turns

    private int minTurnsNumberInSeries;
    private int maxPausesNumberBetweenTurns;

    private boolean singleTurnZoneFullTime;
    private int singleTurnZoneBeginOffset;
    private int singleTurnZoneEndOffset;

    private boolean groupOfTurnsZoneFullTime;
    private int groupOfTurnsZoneBeginOffset;
    private int groupOfTurnsZoneEndOffset;

    // properties

    private boolean verified;
    private boolean deleted;
}
