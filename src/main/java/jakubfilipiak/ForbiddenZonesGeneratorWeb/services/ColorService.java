package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.MapConfigSingleton;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
@Service
public class ColorService {

    public boolean isColorForbidden(Color pixelColor) {

        Color forbiddenColors = MapConfigSingleton.INSTANCE.getForbiddenColor();
        return forbiddenColors.equals(pixelColor);
    }

    public String fromColor(Color color) {
        String r = String.valueOf(color.getRed());
        String g = String.valueOf(color.getGreen());
        String b = String.valueOf(color.getBlue());
        return r + ", " + g + ", " + b;
    }

    public Color fromString(String rgb) {
        String[] singleColors = rgb.split(",");
        final int rIndex = 0;
        final int gIndex = 1;
        final int bIndex = 2;

        int r = Integer.parseInt(singleColors[rIndex].trim());
        int g = Integer.parseInt(singleColors[gIndex].trim());
        int b = Integer.parseInt(singleColors[bIndex].trim());
        return new Color(r, g, b);
    }
}
