package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Filipiak on 04.06.2019.
 */
public class NeighborhoodCreator {

    public List<Coordinates> getPixelNeighbors(Coordinates pixelCoordinates,
                                               int radiusOfPixelsToBeVerified) {
        List<Coordinates> neighbors = new ArrayList<>();
        if (radiusOfPixelsToBeVerified <= 0) {
            return neighbors;
        }

        int sideOfNeighborsSquare = radiusOfPixelsToBeVerified * 2 + 1;
        int startPixelX = pixelCoordinates.getPixelX() - radiusOfPixelsToBeVerified;
        int startPixelY = pixelCoordinates.getPixelY() - radiusOfPixelsToBeVerified;


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
