package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TurnOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.TurnAngleCalculator;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jakub Filipiak on 04.07.2019.
 */
public class TurnOfTrackGeneratorTest {

    private TurnAngleCalculator turnAngleCalculator;
    private TurnOfTrackGenerator turnOfTrackGenerator;
    // PointOfTrack names convention: point00 - x=0, y=0; point01 - x=0, y=1...
    private PointOfTrack point00;
    private PointOfTrack point01;
    private PointOfTrack point12;
    private PointOfTrack point11;

    @Before
    public void setUp() throws Exception {
        turnAngleCalculator = mock(TurnAngleCalculator.class);
        turnOfTrackGenerator = new TurnOfTrackGenerator(turnAngleCalculator);
        point00 = PointOfTrack.builder()
                .longitude(0d)
                .latitude(0d)
                .build();
        point01 = PointOfTrack.builder()
                .longitude(0d)
                .latitude(1d)
                .build();
        point12 = PointOfTrack.builder()
                .longitude(1d)
                .latitude(2d)
                .build();
        point11 = PointOfTrack.builder()
                .longitude(1d)
                .latitude(1d)
                .build();
    }

    @Test
    public void shouldReturnEmptyOptionalWhenBufferUpdatedByFirstPoint() {
        // given
        PointOfTrack point1 = point00;
        Optional<TurnOfTrack> expectedTurn = Optional.empty();

        // when
        turnOfTrackGenerator.updatePointsBuffer(point1);
        Optional<TurnOfTrack> resultTurn = turnOfTrackGenerator.createTurnFromBuffer();

        // then
        assertThat(resultTurn).isEqualTo(expectedTurn);
    }

    @Test
    public void shouldReturnEmptyOptionalWhenBufferUpdatedByFirstTwoPoints() {
        // given
        PointOfTrack point1 = point00;
        PointOfTrack point2 = point01;
        Optional<TurnOfTrack> expectedTurn = Optional.empty();

        // when
        turnOfTrackGenerator.updatePointsBuffer(point1);
        turnOfTrackGenerator.updatePointsBuffer(point2);
        Optional<TurnOfTrack> resultTurn = turnOfTrackGenerator.createTurnFromBuffer();

        // then
        assertThat(resultTurn).isEqualTo(expectedTurn);
    }

    @Test
    public void shouldCorrectReturnTurnWhenBufferUpdatedByFirstThreePoints() {
        // given
        PointOfTrack point1 = point00;
        PointOfTrack point2 = point01;
        PointOfTrack point3 = point12;
        when(turnAngleCalculator.calculateAbsoluteAngleOfLine(point1, point2))
                .thenReturn(90d);
        when(turnAngleCalculator.calculateAbsoluteAngleOfLine(point2, point3))
                .thenReturn(45d);
        when(turnAngleCalculator.calculateTurnAngle(any(double.class),
                any(double.class)))
                .thenReturn(45d);
        Optional<TurnOfTrack> expectedTurn = Optional.of(TurnOfTrack
                .builder()
                .entrancePoint(point1)
                .middlePoint(point2)
                .departurePoint(point3)
                .absoluteEntranceAngle(90d)
                .absoluteDepartureAngle(45d)
                .angle(45d)
                .build());

        // when
        turnOfTrackGenerator.updatePointsBuffer(point1);
        turnOfTrackGenerator.updatePointsBuffer(point2);
        turnOfTrackGenerator.updatePointsBuffer(point3);
        Optional<TurnOfTrack> resultTurn = turnOfTrackGenerator.createTurnFromBuffer();

        // then
        assertThat(resultTurn).isEqualTo(expectedTurn);
    }

    @Test
    public void shouldCorrectReturnTurnWhenBufferUpdatedByMoreThanThreePoints() {
        // given
        PointOfTrack point1 = point00;
        PointOfTrack point2 = point01;
        PointOfTrack point3 = point12;
        PointOfTrack point4 = point11;
        when(turnAngleCalculator.calculateAbsoluteAngleOfLine(point2, point3))
                .thenReturn(45d);
        when(turnAngleCalculator.calculateAbsoluteAngleOfLine(point3, point4))
                .thenReturn(-90d);
        when(turnAngleCalculator.calculateTurnAngle(any(double.class),
                any(double.class)))
                .thenReturn(45d);
        Optional<TurnOfTrack> expectedTurn = Optional.of(TurnOfTrack
                .builder()
                .entrancePoint(point2)
                .middlePoint(point3)
                .departurePoint(point4)
                .absoluteEntranceAngle(45d)
                .absoluteDepartureAngle(-90d)
                .angle(45d)
                .build());

        // when
        turnOfTrackGenerator.updatePointsBuffer(point1);
        turnOfTrackGenerator.updatePointsBuffer(point2);
        turnOfTrackGenerator.updatePointsBuffer(point3);
        turnOfTrackGenerator.updatePointsBuffer(point4);
        Optional<TurnOfTrack> resultTurn = turnOfTrackGenerator.createTurnFromBuffer();

        // then
        assertThat(resultTurn).isEqualTo(expectedTurn);
    }
}