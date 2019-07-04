package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.TurnAngleCalculator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jakub Filipiak on 08.06.2019.
 */
public class TurnAngleCalculatorTest {

    private TurnAngleCalculator turnAngleCalculator = new TurnAngleCalculator();

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenHorizontalRightDirection() {
        // given
        PointOfTrack beginPoint = PointOfTrack.builder()
                .longitude(0f)
                .latitude(0f)
                .build();
        PointOfTrack endPoint = PointOfTrack.builder()
                .longitude(1f)
                .latitude(0f)
                .build();
        double expectedAbsoluteAngle = 0d;

        // when
        double resultAbsoluteAngle =
                turnAngleCalculator.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenHorizontalLeftDirection() {
        // given
        PointOfTrack beginPoint = PointOfTrack.builder()
                .longitude(0f)
                .latitude(0f)
                .build();
        PointOfTrack endPoint = PointOfTrack.builder()
                .longitude(-1f)
                .latitude(0f)
                .build();
        double expectedAbsoluteAngle = 180d;

        // when
        double resultAbsoluteAngle =
                turnAngleCalculator.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenVerticalUpDirection() {
        // given
        PointOfTrack beginPoint = PointOfTrack.builder()
                .longitude(0f)
                .latitude(0f)
                .build();
        PointOfTrack endPoint = PointOfTrack.builder()
                .longitude(0f)
                .latitude(1f)
                .build();
        double expectedAbsoluteAngle = 90d;

        // when
        double resultAbsoluteAngle =
                turnAngleCalculator.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenVerticalDownDirection() {
        // given
        PointOfTrack beginPoint = PointOfTrack.builder()
                .longitude(0f)
                .latitude(0f)
                .build();
        PointOfTrack endPoint = PointOfTrack.builder()
                .longitude(0f)
                .latitude(-1f)
                .build();
        double expectedAbsoluteAngle = -90d;

        // when
        double resultAbsoluteAngle =
                turnAngleCalculator.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateTurnAngleWhenBorderAnglesTheSame() {
        // given
        double entranceAngle = 45d;
        double departureAngle = 45d;
        double expectedCalculatedAngle = 0d;

        // when
        double resultCalculatedAngle =
                turnAngleCalculator.calculateTurnAngle(entranceAngle, departureAngle);

        // then
        Assert.assertEquals(expectedCalculatedAngle, resultCalculatedAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateTurnAngleWhenBorderAnglesPositive() {
        // given
        double entranceAngle = 135d;
        double departureAngle = 90d;
        double expectedCalculatedAngle = 45d;

        // when
        double resultCalculatedAngle =
                turnAngleCalculator.calculateTurnAngle(entranceAngle, departureAngle);

        // then
        Assert.assertEquals(expectedCalculatedAngle, resultCalculatedAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateTurnAngleWhenBorderAnglesNegative() {
        // given
        double entranceAngle = -135d;
        double departureAngle = -90d;
        double expectedCalculatedAngle = 45d;

        // when
        double resultCalculatedAngle =
                turnAngleCalculator.calculateTurnAngle(entranceAngle, departureAngle);

        // then
        Assert.assertEquals(expectedCalculatedAngle, resultCalculatedAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateTurnAngleWhenBorderAnglesDifferentSignAndAbsSumBiggerThan180() {
        // given
        double entranceAngle = 180d;
        double departureAngle = -135d;
        double expectedCalculatedAngle = 45d;

        // when
        double resultCalculatedAngle =
                turnAngleCalculator.calculateTurnAngle(entranceAngle, departureAngle);

        // then
        Assert.assertEquals(expectedCalculatedAngle, resultCalculatedAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateTurnAngleWhenBorderAnglesDifferentSignAndAbsSumNotBiggerThan180() {
        // given
        double entranceAngle = 45d;
        double departureAngle = -45d;
        double expectedCalculatedAngle = 90d;

        // when
        double resultCalculatedAngle =
                turnAngleCalculator.calculateTurnAngle(entranceAngle, departureAngle);

        // then
        Assert.assertEquals(expectedCalculatedAngle, resultCalculatedAngle, 0.001d);
    }
}
