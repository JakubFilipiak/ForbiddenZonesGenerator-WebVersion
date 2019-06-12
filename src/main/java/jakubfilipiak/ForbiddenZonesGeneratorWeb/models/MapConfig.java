package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Getter
@Setter
public class MapConfig {

    private String filePathname;

    private List<Color> allowedColors;
    private List<Color> forbiddenColors;

    private float bottomLeftCornerLatitude;
    private float bottomLeftCornerLongitude;

    private float upperRightCornerLatitude;
    private float upperRightCornerLongitude;
}
