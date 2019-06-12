package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.MapPropertiesSingleton;

import java.awt.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class ColorService {

    public boolean isColorForbidden(Color pixelColor) {

        List<Color> forbiddenColors = MapPropertiesSingleton.INSTANCE.getForbiddenColors();
        return forbiddenColors.contains(pixelColor);
    }
}
