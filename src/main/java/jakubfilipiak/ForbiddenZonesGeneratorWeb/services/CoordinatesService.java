package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 04.06.2019.
 */
public class CoordinatesService {

    public List<Coordinates> getPixelNeighbors(Coordinates pixelCoordinates,
                                               int RadiusOfPixelsToBeVerified) {
        int sideOfNeighborsSquare = RadiusOfPixelsToBeVerified * 2 + 1;
        int startPixelX = pixelCoordinates.getPixelX() - RadiusOfPixelsToBeVerified;
        int startPixelY = pixelCoordinates.getPixelY() - RadiusOfPixelsToBeVerified;
        List<Coordinates> neighbors = new ArrayList<>();

        for (int rows = 0; rows < sideOfNeighborsSquare; rows++) {
            int actualPixelY = startPixelY + rows;
            for (int columns = 0; columns < sideOfNeighborsSquare; columns++) {
                int actualPixelX = startPixelX + columns;
                Coordinates neighborCoordinates =
                        new Coordinates(actualPixelX, actualPixelY);
                if (!pixelCoordinates.equals(neighborCoordinates))
                    neighbors.add(neighborCoordinates);
            }
        }
        return neighbors;
    }
}
