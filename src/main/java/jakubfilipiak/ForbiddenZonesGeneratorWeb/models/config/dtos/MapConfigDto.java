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
public class MapConfigDto {

    private String configName;

    private String originalFileName;
    private String uniqueFileName;

    private String allowedRGBColor;
    private String forbiddenRGBColor;

    private double bottomLeftCornerLatitude;
    private double bottomLeftCornerLongitude;

    private double upperRightCornerLatitude;
    private double upperRightCornerLongitude;

    // properties

    private boolean verified;
    private boolean deleted;
}
