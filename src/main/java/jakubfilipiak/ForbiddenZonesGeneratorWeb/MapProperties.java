package jakubfilipiak.ForbiddenZonesGeneratorWeb;

import java.awt.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public enum MapProperties {

    INSTANCE;

    private String filePathname;

    private float bottomLeftCornerLatitude;
    private float bottomLeftCornerLongitude;

    private float upperRightCornerLatitude;
    private float upperRightCornerLongitude;

    private List<Color> allowedColors;
    private List<Color> forbiddenColors;

    public String getFilePathname() {
        return filePathname;
    }

    public List<Color> getAllowedColors() {
        return allowedColors;
    }

    public List<Color> getForbiddenColors() {
        return forbiddenColors;
    }

    public float getRelativeLongitudeZero(){
        return bottomLeftCornerLongitude;
    }

    public float getRelativeLatitudeZero() {
        return upperRightCornerLatitude;
    }

    public float getLongitudeResolution(){
        return upperRightCornerLongitude - bottomLeftCornerLongitude;
    }

    public float getLatitudeResolution(){
        return upperRightCornerLatitude - bottomLeftCornerLatitude;
    }
}
