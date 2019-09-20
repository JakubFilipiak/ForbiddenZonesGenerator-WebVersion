package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public enum TypeOfFile {

    TRK(".trk"),
    PNG(".png"),
    TXT(".txt"),
    UNKNOWN(".unknown");

    private String type;

    TypeOfFile(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static TypeOfFile fromString(String type) {

        for (TypeOfFile typeOfFile : TypeOfFile.values()) {
            if (typeOfFile.toString().equals(type.toLowerCase())) {
                return typeOfFile;
            }
        }
        return UNKNOWN;
    }
}
