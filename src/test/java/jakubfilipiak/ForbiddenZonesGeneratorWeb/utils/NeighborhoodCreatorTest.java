package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by Jakub Filipiak on 20.07.2019.
 */
public class NeighborhoodCreatorTest {

    private NeighborhoodCreator neighborhoodCreator;

    @Before
    public void setUp() throws Exception {
        neighborhoodCreator = new NeighborhoodCreator();
    }

    @Test
    public void shouldReturnEmptyListWhenRadiusIsZero() {
        // given
        Coordinates baseCoordinates = new Coordinates(1000, 1000);
        int radiusOfPixels = 0;

        // when
        List<Coordinates> resultNeighbors =
                neighborhoodCreator.getPixelNeighbors(baseCoordinates, radiusOfPixels);

        // then
        assertThat(resultNeighbors.size()).isZero();
    }

    @Test
    public void shouldReturnEmptyListWhenRadiusIsLessThanZero() {
        // given
        Coordinates baseCoordinates = new Coordinates(1000, 1000);
        int radiusOfPixels = -3;

        // when
        List<Coordinates> resultNeighbors =
                neighborhoodCreator.getPixelNeighbors(baseCoordinates, radiusOfPixels);

        // then
        assertThat(resultNeighbors.size()).isZero();
    }

    @Test
    public void shouldCorrectReturnNeighborsWhenRadiusIsOne() {
        // given
        Coordinates baseCoordinates = new Coordinates(1000, 1000);
        int radiusOfPixels = 1;
        List<Coordinates> expectedNeighbors = new ArrayList<>(Arrays.asList(
                new Coordinates(999, 1001),
                new Coordinates(1000, 1001),
                new Coordinates(1001, 1001),

                new Coordinates(999, 1000),
                new Coordinates(1001, 1000),

                new Coordinates(999, 999),
                new Coordinates(1000, 999),
                new Coordinates(1001, 999)
        ));

        // when
        List<Coordinates> resultNeighbors =
                neighborhoodCreator.getPixelNeighbors(baseCoordinates, radiusOfPixels);

        // then
        assertThat(resultNeighbors.size()).isEqualTo(8);
        assertThat(resultNeighbors).containsExactlyInAnyOrderElementsOf(expectedNeighbors);
    }

    @Test
    public void shouldCorrectReturnNeighborsWhenRadiusIsThree() {
        // given
        Coordinates baseCoordinates = new Coordinates(1000, 1000);
        int radiusOfPixels = 3;
        List<Coordinates> expectedNeighbors = new ArrayList<>(Arrays.asList(
                new Coordinates(997, 1003),
                new Coordinates(998, 1003),
                new Coordinates(999, 1003),
                new Coordinates(1000, 1003),
                new Coordinates(1001, 1003),
                new Coordinates(1002, 1003),
                new Coordinates(1003, 1003),

                new Coordinates(997, 1002),
                new Coordinates(998, 1002),
                new Coordinates(999, 1002),
                new Coordinates(1000, 1002),
                new Coordinates(1001, 1002),
                new Coordinates(1002, 1002),
                new Coordinates(1003, 1002),

                new Coordinates(997, 1001),
                new Coordinates(998, 1001),
                new Coordinates(999, 1001),
                new Coordinates(1000, 1001),
                new Coordinates(1001, 1001),
                new Coordinates(1002, 1001),
                new Coordinates(1003, 1001),

                new Coordinates(997, 1000),
                new Coordinates(998, 1000),
                new Coordinates(999, 1000),
                new Coordinates(1001, 1000),
                new Coordinates(1002, 1000),
                new Coordinates(1003, 1000),

                new Coordinates(997, 999),
                new Coordinates(998, 999),
                new Coordinates(999, 999),
                new Coordinates(1000, 999),
                new Coordinates(1001, 999),
                new Coordinates(1002, 999),
                new Coordinates(1003, 999),

                new Coordinates(997, 998),
                new Coordinates(998, 998),
                new Coordinates(999, 998),
                new Coordinates(1000, 998),
                new Coordinates(1001, 998),
                new Coordinates(1002, 998),
                new Coordinates(1003, 998),

                new Coordinates(997, 997),
                new Coordinates(998, 997),
                new Coordinates(999, 997),
                new Coordinates(1000, 997),
                new Coordinates(1001, 997),
                new Coordinates(1002, 997),
                new Coordinates(1003, 997)
        ));

        // when
        List<Coordinates> resultNeighbors =
                neighborhoodCreator.getPixelNeighbors(baseCoordinates, radiusOfPixels);

        // then
        assertThat(resultNeighbors.size()).isEqualTo(48);
        assertThat(resultNeighbors).containsExactlyInAnyOrderElementsOf(expectedNeighbors);
    }
}