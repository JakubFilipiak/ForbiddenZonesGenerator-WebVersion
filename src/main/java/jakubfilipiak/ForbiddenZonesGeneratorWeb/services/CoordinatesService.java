package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.Properties;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 04.06.2019.
 */
public class CoordinatesService {

    public List<Coordinates> getPixelNeighbors(Coordinates pixelCoordinates) {

        int pixelRadius = Properties.INSTANCE.getRadiusOfPixelsToBeVerified();
        int sideOfNeighborsSquare = pixelRadius * 2 + 1;
        int startPixelX = pixelCoordinates.getPixelX() - pixelRadius;
        int startPixelY = pixelCoordinates.getPixelY() - pixelRadius;
        List<Coordinates> neighbors = new ArrayList<>();

        for (int rows = 0; rows < sideOfNeighborsSquare; rows++) {
            int actualPixelY = startPixelY + rows;
            for (int columns = 0; columns < sideOfNeighborsSquare; columns++) {
                int actualPixelX = startPixelX + columns;
                Coordinates neighborCoordinates = new Coordinates(actualPixelX, actualPixelY);
                if (!pixelCoordinates.equals(neighborCoordinates))
                    neighbors.add(neighborCoordinates);
            }
        }

        return neighbors;
    }
}
