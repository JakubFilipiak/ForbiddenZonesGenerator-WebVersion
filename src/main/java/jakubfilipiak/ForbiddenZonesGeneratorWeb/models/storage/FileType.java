package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public enum FileType {

    TRK(".trk"),
    PNG(".png"),
    TXT(".txt"),
    UNKNOWN(".unknown");

    private String type;

    FileType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static FileType fromString(String type) {

        for (FileType fileType : FileType.values()) {
            if (fileType.toString().equals(type)) {
                return fileType;
            }
        }
        return UNKNOWN;
    }
}
