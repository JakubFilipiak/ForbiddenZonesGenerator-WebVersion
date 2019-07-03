package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.Coordinates;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.io.PngReader;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jakub Filipiak on 03.07.2019.
 */
public class MapConfigValidator {

    // COMPONENT_RANGE_REGEX = 0-9 || 10-99 || 100-199 || 200-249 || 250-255
    private static final String COMPONENT_RANGE_REGEX = "([0-9]" +
                    "|[1-9][0-9]" +
                    "|1[0-9][0-9]" +
                    "|2[0-4][0-9]" +
                    "|25[0-5])";
    // COMPONENT_SEPARATOR_REGEX = *,*
    private static final String COMPONENT_SEPARATOR_REGEX = "(\\s*,\\s*)";
    // RGB_REGEX = 0-255*,*0-255*,*0-255
    private static final String RGB_REGEX = COMPONENT_RANGE_REGEX +
                    COMPONENT_SEPARATOR_REGEX +
                    COMPONENT_RANGE_REGEX +
                    COMPONENT_SEPARATOR_REGEX +
                    COMPONENT_RANGE_REGEX;

    private MapConfig config;

    public MapConfigValidator(MapConfig config) {
        this.config = config;
    }

    public boolean isEachCoordinateCorrect() {
        return isLatitudeTrendCorrect() && isLongitudeTrendCorrect();
    }

    public boolean isColorsDefinitionCorrect() {
        return isRGBFormatCorrect() && isEachColorDifferent();
    }

    public boolean isPNGMapCorrect() {
        Set<Color> expectedColors = new HashSet<>(Arrays.asList(
                createColorFromStringRGB(config.getAllowedRGBColor()),
                createColorFromStringRGB(config.getForbiddenRGBColor())
        ));
        Set<Color> mapColors = getPNGMapColors();
        return mapColors.size() == expectedColors.size() &&
                mapColors.containsAll(expectedColors);
    }

    private boolean isLatitudeTrendCorrect() {
        return config.getBottomLeftCornerLatitude()
                < config.getUpperRightCornerLatitude();
    }

    private boolean isLongitudeTrendCorrect() {
        return config.getBottomLeftCornerLongitude()
                < config.getUpperRightCornerLongitude();
    }

    private boolean isRGBFormatCorrect() {
        return isEachRGBComponentInCorrectRange(config.getAllowedRGBColor()) &&
                isEachRGBComponentInCorrectRange(config.getForbiddenRGBColor());
    }

    private boolean isEachRGBComponentInCorrectRange(String rgbColor) {
        return rgbColor.matches(RGB_REGEX);
    }

    private boolean isEachColorDifferent() {
        Color allowedColor = createColorFromStringRGB(config.getAllowedRGBColor());
        Color forbiddenColor = createColorFromStringRGB(config.getForbiddenRGBColor());
        return !allowedColor.equals(forbiddenColor);
    }

    public Color createColorFromStringRGB(String rgb) {
        String[] singleColors = rgb.split(",");
        final int rIndex = 0;
        final int gIndex = 1;
        final int bIndex = 2;

        int r = Integer.parseInt(singleColors[rIndex].trim());
        int g = Integer.parseInt(singleColors[gIndex].trim());
        int b = Integer.parseInt(singleColors[bIndex].trim());
        return new Color(r, g, b);
    }

    private Set<Color> getPNGMapColors() {
        Set<Color> mapColors = new HashSet<>();
        PngReader pngReader = new PngReader(config);
        int mapWidth = pngReader.getImageWidth();
        int mapHeight = pngReader.getImageHeight();
        int pixelX;
        int pixelY;
        Coordinates coordinates;
        Color color;

        for (pixelX = 0; pixelX < mapWidth; pixelX++) {
            for (pixelY = 0; pixelY < mapHeight; pixelY++) {
                coordinates = new Coordinates(pixelX, pixelY);
                color = pngReader.getPixelColor(coordinates);
                mapColors.add(color);
            }
        }
        pngReader.close();
        return mapColors;
    }
}
