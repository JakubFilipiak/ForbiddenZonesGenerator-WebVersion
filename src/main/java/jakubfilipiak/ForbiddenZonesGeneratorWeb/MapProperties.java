package jakubfilipiak.ForbiddenZonesGeneratorWeb;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public enum MapProperties {

    INSTANCE;

    @Getter @Setter private String filePathname;

    @Getter @Setter private List<Color> allowedColors;
    @Getter @Setter private List<Color> forbiddenColors;

    @Setter private float bottomLeftCornerLatitude;
    @Setter private float bottomLeftCornerLongitude;

    @Setter private float upperRightCornerLatitude;
    @Setter private float upperRightCornerLongitude;


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
