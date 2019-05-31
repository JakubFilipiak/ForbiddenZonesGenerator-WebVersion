package jakubfilipiak.ForbiddenZonesGeneratorWeb;

/**
 * Created by Jakub Filipiak on 30.05.2019.
 */
public enum MapProperties {

    INSTANCE;

    private String filePathname = "";

    private float bottomLeftCornerLatitude;
    private float bottomLeftCornerLongitude;

    private float upperRightCornerLatitude;
    private float upperRightCornerLongitude;

    public String getFilePathname() {
        return filePathname;
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
