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
public class ZoneByPointsTimeConfigDto {

    private String configName;

    private int singlePointZoneBeginOffset;
    private int singlePointZoneEndOffset;

    private int groupOfPointsZoneBeginOffset;
    private int groupOfPointsZoneEndOffset;

    private boolean verified;
    private boolean deleted;
}
