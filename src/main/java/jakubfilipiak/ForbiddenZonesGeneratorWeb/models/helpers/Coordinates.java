package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers;

import java.util.Objects;

/**
 * Created by Jakub Filipiak on 04.06.2019.
 */
public class Coordinates {

    private int pixelX;
    private int pixelY;

    public Coordinates(int pixelX, int pixelY) {
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    public int getPixelX() {
        return pixelX;
    }

    public int getPixelY() {
        return pixelY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return pixelX == that.pixelX &&
                pixelY == that.pixelY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pixelX, pixelY);
    }
}
