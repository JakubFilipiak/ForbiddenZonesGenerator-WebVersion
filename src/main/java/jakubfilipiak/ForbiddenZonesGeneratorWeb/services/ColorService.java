package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.MapProperties;

import java.awt.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class ColorService {

    public boolean isColorForbidden(Color pixelColor) {

        List<Color> forbiddenColors = MapProperties.INSTANCE.getForbiddenColors();
        return forbiddenColors.contains(pixelColor);
    }
}
