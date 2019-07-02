package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import java.awt.*;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public class ColorChecker {

    private Color forbiddenColor;

    public ColorChecker(String forbiddenRGBColor) {
        this.forbiddenColor = fromString(forbiddenRGBColor);
    }

    public boolean isColorForbidden(Color pixelColor) {
        return pixelColor.equals(forbiddenColor);
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
