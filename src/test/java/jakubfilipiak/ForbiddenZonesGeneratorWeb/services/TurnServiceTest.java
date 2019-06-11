package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.PointOfTrack;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jakub Filipiak on 08.06.2019.
 */
public class TurnServiceTest {

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenHorizontalRightDirection() {
        // given
        PointOfTrack beginPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(0f)
                .setLatitude(0f)
                .build();
        PointOfTrack endPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(1f)
                .setLatitude(0f)
                .build();
        double expectedAbsoluteAngle = 0d;

        // when
        double resultAbsoluteAngle =
                TurnService.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenHorizontalLeftDirection() {
        // given
        PointOfTrack beginPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(0f)
                .setLatitude(0f)
                .build();
        PointOfTrack endPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(-1f)
                .setLatitude(0f)
                .build();
        double expectedAbsoluteAngle = 180d;

        // when
        double resultAbsoluteAngle =
                TurnService.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenVerticalUpDirection() {
        // given
        PointOfTrack beginPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(0f)
                .setLatitude(0f)
                .build();
        PointOfTrack endPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(0f)
                .setLatitude(1f)
                .build();
        double expectedAbsoluteAngle = 90d;

        // when
        double resultAbsoluteAngle =
                TurnService.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

        // then
        Assert.assertEquals(expectedAbsoluteAngle, resultAbsoluteAngle, 0.001d);
    }

    @Test
    public void shouldCorrectCalculateAbsoluteAngleOfLineWhenVerticalDownDirection() {
        // given
        PointOfTrack beginPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(0f)
                .setLatitude(0f)
                .build();
        PointOfTrack endPoint = new PointOfTrack.PointOfTrackBuilder()
                .setLongitude(0f)
                .setLatitude(-1f)
                .build();
        double expectedAbsoluteAngle = -90d;

        // when
        double resultAbsoluteAngle =
                TurnService.calculateAbsoluteAngleOfLine(beginPoint, endPoint);

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
                TurnService.calculateTurnAngle(entranceAngle, departureAngle);

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
                TurnService.calculateTurnAngle(entranceAngle, departureAngle);

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
                TurnService.calculateTurnAngle(entranceAngle, departureAngle);

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
                TurnService.calculateTurnAngle(entranceAngle, departureAngle);

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
                TurnService.calculateTurnAngle(entranceAngle, departureAngle);

        // then
        Assert.assertEquals(expectedCalculatedAngle, resultCalculatedAngle, 0.001d);
    }
}
