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

    private boolean pointsMultiplication;

    private boolean pointNeighborhoodVerification;
    private int radiusOfPixelsToBeVerified;

    private int minPointsNumberInSeries;
    private int maxPausesNumberBetweenPoints;

    private boolean verified;
    private boolean deleted;
}
