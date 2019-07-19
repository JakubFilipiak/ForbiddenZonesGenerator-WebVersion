package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos;

import lombok.*;

/**
 * Created by Jakub Filipiak on 14.07.2019.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneByTurnsTimeConfigDto {

    private String configName;

    private boolean singleTurnZoneFullTime;
    private int singleTurnZoneBeginOffset;
    private int singleTurnZoneEndOffset;

    private boolean groupOfTurnsZoneFullTime;
    private int groupOfTurnsZoneBeginOffset;
    private int groupOfTurnsZoneEndOffset;

    private boolean verified;
    private boolean deleted;
}
