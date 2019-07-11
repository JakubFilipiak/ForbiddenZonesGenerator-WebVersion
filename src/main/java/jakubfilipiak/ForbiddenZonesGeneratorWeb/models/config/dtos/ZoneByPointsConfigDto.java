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
public class ZoneByPointsConfigDto {

    private String configName;

    // point

    private boolean pointsMultiplication;

    private boolean pointNeighborhoodVerification;
    private int radiusOfPixelsToBeVerified;

    // zone forbidden by points

    private int minPointsNumberInSeries;
    private int maxPausesNumberBetweenPoints;

    private int singlePointZoneBeginOffset;
    private int singlePointZoneEndOffset;

    private int groupOfPointsZoneBeginOffset;
    private int groupOfPointsZoneEndOffset;

    // properties

    private boolean verified;
    private boolean deleted;
}
