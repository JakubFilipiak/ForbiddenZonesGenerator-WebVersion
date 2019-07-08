package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Jakub Filipiak on 08.07.2019.
 */
public class ForbiddenZoneTest {

    private final int ZONE_SMALLER = -1;
    private final int ZONE_SAME = 0;
    private final int ZONE_BIGGER = 1;

    private ZoneByTurnsConfig zoneByTurnsConfig;
    private TurnOfTrack entranceTurn;
    private TurnOfTrack departureTurn;

    private ZoneByPointsConfig zoneByPointsConfig;
    private PointOfTrack entrancePoint;
    private PointOfTrack departurePoint;

    private ForbiddenZone originalZone;
    private ForbiddenZone zoneWithLaterEntranceTime;
    private ForbiddenZone zoneWithEarlierEntranceTime;
    private ForbiddenZone zoneWithSameEntranceTimeAndLaterDepartureTime;
    private ForbiddenZone zoneWithSameEntranceTimeAndEarlierDepartureTime;
    private ForbiddenZone sameZone;

    @Before
    public void setUp() throws Exception {
        LocalTime entranceTimeOfEntranceTurn = LocalTime.of(8, 0, 0);
        LocalTime middleTimeOfEntranceTurn = LocalTime.of(8, 0, 5);
        LocalTime departureTimeOfEntranceTurn = LocalTime.of(8, 0, 10);

        LocalTime entranceTimeOfDepartureTurn = LocalTime.of(8, 1, 0);
        LocalTime middleTimeOfDepartureTurn = LocalTime.of(8, 1, 5);
        LocalTime departureTimeOfDepartureTurn = LocalTime.of(8, 1, 10);

        zoneByTurnsConfig = mock(ZoneByTurnsConfig.class);

        entranceTurn = TurnOfTrack.builder()
                .entrancePoint(PointOfTrack.builder()
                        .time(entranceTimeOfEntranceTurn)
                        .build())
                .middlePoint(PointOfTrack.builder()
                        .time(middleTimeOfEntranceTurn)
                        .build())
                .departurePoint(PointOfTrack.builder()
                        .time(departureTimeOfEntranceTurn)
                        .build())
                .build();
        departureTurn = TurnOfTrack.builder()
                .entrancePoint(PointOfTrack.builder()
                        .time(entranceTimeOfDepartureTurn)
                        .build())
                .middlePoint(PointOfTrack.builder()
                        .time(middleTimeOfDepartureTurn)
                        .build())
                .departurePoint(PointOfTrack.builder()
                        .time(departureTimeOfDepartureTurn)
                        .build())
                .build();

        LocalTime timeOfEntrancePoint = LocalTime.of(12, 0, 0);
        LocalTime timeOfDeparturePoint = LocalTime.of(12, 20, 0);

        zoneByPointsConfig = mock(ZoneByPointsConfig.class);

        entrancePoint = PointOfTrack.builder()
                .time(timeOfEntrancePoint)
                .build();
        departurePoint = PointOfTrack.builder()
                .time(timeOfDeparturePoint)
                .build();

        LocalTime entranceTime = LocalTime.of(12, 0);
        LocalTime departureTime = LocalTime.of(12, 15);

        LocalTime laterEntranceTime = LocalTime.of(12, 5);
        LocalTime earlierEntranceTime = LocalTime.of(11, 55);

        LocalTime laterDepartureTime = LocalTime.of(12, 20);
        LocalTime earlierDepartureTime = LocalTime.of(12, 10);

        originalZone = ForbiddenZone.fromTimestamps(entranceTime, departureTime);
        sameZone = ForbiddenZone.fromTimestamps(entranceTime, departureTime);
        zoneWithLaterEntranceTime =
                ForbiddenZone.fromTimestamps(laterEntranceTime, departureTime);
        zoneWithEarlierEntranceTime =
                ForbiddenZone.fromTimestamps(earlierEntranceTime, departureTime);
        zoneWithSameEntranceTimeAndLaterDepartureTime =
                ForbiddenZone.fromTimestamps(entranceTime, laterDepartureTime);
        zoneWithSameEntranceTimeAndEarlierDepartureTime =
                ForbiddenZone.fromTimestamps(entranceTime, earlierDepartureTime);
    }

    @Test
    public void shouldCorrectCreateZoneFromTimestamps() {
        // given
        LocalTime entranceTime = LocalTime.of(12, 0, 0);
        LocalTime departureTime = LocalTime.of(12, 15, 0);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromTimestamps(entranceTime, departureTime);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTime);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTime);
    }

    @Test
    public void shouldCorrectCreateZoneFromSingleTurnWhenZoneFullTimeWithoutOffsets() {
        // given
        int beginOffset = 0;
        int endOffset = 0;
        when(zoneByTurnsConfig.isSingleTurnZoneFullTime()).thenReturn(true);
        when(zoneByTurnsConfig.getSingleTurnZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByTurnsConfig.getSingleTurnZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entranceTurn.getEntranceTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                entranceTurn.getDepartureTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromSingleTurn(entranceTurn, zoneByTurnsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromSingleTurnWhenZoneFullTimeWithOffsets() {
        // given
        int beginOffset = -1;
        int endOffset = 2;
        when(zoneByTurnsConfig.isSingleTurnZoneFullTime()).thenReturn(true);
        when(zoneByTurnsConfig.getSingleTurnZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByTurnsConfig.getSingleTurnZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entranceTurn.getEntranceTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                entranceTurn.getDepartureTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromSingleTurn(entranceTurn, zoneByTurnsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromSingleTurnWhenZoneNotFullTimeWithoutOffsets() {
        // given
        int beginOffset = 0;
        int endOffset = 0;
        when(zoneByTurnsConfig.isSingleTurnZoneFullTime()).thenReturn(false);
        when(zoneByTurnsConfig.getSingleTurnZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByTurnsConfig.getSingleTurnZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entranceTurn.getMiddleTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                entranceTurn.getMiddleTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromSingleTurn(entranceTurn, zoneByTurnsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromSingleTurnWhenZoneNotFullTimeWithOffsets() {
        // given
        int beginOffset = -1;
        int endOffset = 2;
        when(zoneByTurnsConfig.isSingleTurnZoneFullTime()).thenReturn(false);
        when(zoneByTurnsConfig.getSingleTurnZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByTurnsConfig.getSingleTurnZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entranceTurn.getMiddleTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                entranceTurn.getMiddleTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromSingleTurn(entranceTurn, zoneByTurnsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneGroupOfTurnsWhenZoneFullTimeWithoutOffsets() {
        // given
        int beginOffset = 0;
        int endOffset = 0;
        when(zoneByTurnsConfig.isGroupOfTurnsZoneFullTime()).thenReturn(true);
        when(zoneByTurnsConfig.getGroupOfTurnsZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByTurnsConfig.getGroupOfTurnsZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entranceTurn.getEntranceTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                departureTurn.getDepartureTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromGroupOfTurns(entranceTurn,
                departureTurn, zoneByTurnsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneGroupOfTurnsWhenZoneFullTimeWithOffsets() {
        // given
        int beginOffset = -1;
        int endOffset = 2;
        when(zoneByTurnsConfig.isGroupOfTurnsZoneFullTime()).thenReturn(true);
        when(zoneByTurnsConfig.getGroupOfTurnsZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByTurnsConfig.getGroupOfTurnsZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entranceTurn.getEntranceTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                departureTurn.getDepartureTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromGroupOfTurns(entranceTurn,
                departureTurn, zoneByTurnsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromSinglePointWhenZoneWithoutOffsets() {
        // given
        int beginOffset = 0;
        int endOffset = 0;
        when(zoneByPointsConfig.getSinglePointZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByPointsConfig.getSinglePointZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entrancePoint.getTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                entrancePoint.getTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromSinglePoint(entrancePoint,
                zoneByPointsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromSinglePointWhenZoneWithOffsets() {
        // given
        int beginOffset = -1;
        int endOffset = 2;
        when(zoneByPointsConfig.getSinglePointZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByPointsConfig.getSinglePointZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entrancePoint.getTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                entrancePoint.getTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromSinglePoint(entrancePoint,
                zoneByPointsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromGroupOfPointsWhenZoneWithoutOffsets() {
        // given
        int beginOffset = 0;
        int endOffset = 0;
        when(zoneByPointsConfig.getGroupOfPointsZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByPointsConfig.getGroupOfPointsZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entrancePoint.getTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                departurePoint.getTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromGroupOfPoints(entrancePoint,
                departurePoint, zoneByPointsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCreateZoneFromGroupOfPointsWhenZoneWithOffsets() {
        // given
        int beginOffset = -1;
        int endOffset = 2;
        when(zoneByPointsConfig.getGroupOfPointsZoneBeginOffset()).thenReturn(beginOffset);
        when(zoneByPointsConfig.getGroupOfPointsZoneEndOffset()).thenReturn(endOffset);
        LocalTime entranceTimeOfResultZone =
                entrancePoint.getTime().minusSeconds(beginOffset);
        LocalTime departureTimeOfResultZone =
                departurePoint.getTime().plusSeconds(endOffset);

        // when
        ForbiddenZone resultZone = ForbiddenZone.fromGroupOfPoints(entrancePoint,
                departurePoint, zoneByPointsConfig);

        // then
        assertThat(resultZone).isNotNull();
        assertThat(resultZone.getEntranceTime()).isEqualTo(entranceTimeOfResultZone);
        assertThat(resultZone.getDepartureTime()).isEqualTo(departureTimeOfResultZone);
    }

    @Test
    public void shouldCorrectCompareZonesWhenArgumentOfComparisonHasLaterEntranceTime() {
        // given
        ForbiddenZone zone = originalZone;
        ForbiddenZone comparedZone = zoneWithLaterEntranceTime;

        // when
        int resultOfComparison = zone.compareTo(comparedZone);

        // then
        assertThat(resultOfComparison).isEqualTo(ZONE_SMALLER);
    }

    @Test
    public void shouldCorrectCompareZonesWhenArgumentOfComparisonHasEarlierEntranceTime() {
        // given
        ForbiddenZone zone = originalZone;
        ForbiddenZone comparedZone = zoneWithEarlierEntranceTime;

        // when
        int resultOfComparison = zone.compareTo(comparedZone);

        // then
        assertThat(resultOfComparison).isEqualTo(ZONE_BIGGER);
    }

    @Test
    public void shouldCorrectCompareZonesWhenArgumentOfComparisonHasSameEntranceTimeAndLaterDepartureTime() {
        // given
        ForbiddenZone zone = originalZone;
        ForbiddenZone comparedZone = zoneWithSameEntranceTimeAndLaterDepartureTime;

        // when
        int resultOfComparison = zone.compareTo(comparedZone);

        // then
        assertThat(resultOfComparison).isEqualTo(ZONE_SMALLER);
    }

    @Test
    public void shouldCorrectCompareZonesWhenArgumentOfComparisonHasSameEntranceTimeAndEarlierDepartureTime() {
        // given
        ForbiddenZone zone = originalZone;
        ForbiddenZone comparedZone = zoneWithSameEntranceTimeAndEarlierDepartureTime;

        // when
        int resultOfComparison = zone.compareTo(comparedZone);

        // then
        assertThat(resultOfComparison).isEqualTo(ZONE_BIGGER);
    }

    @Test
    public void shouldCorrectCompareZonesWhenArgumentOfComparisonIsSame() {
        // given
        ForbiddenZone zone = originalZone;
        ForbiddenZone comparedZone = sameZone;

        // when
        int resultOfComparison = zone.compareTo(comparedZone);

        // then
        assertThat(resultOfComparison).isEqualTo(ZONE_SAME);
    }

    @Test
    public void shouldCorrectVerifyEqualsAndHashCode() {
        EqualsVerifier.forClass(ForbiddenZone.class)
                .usingGetClass()
                .withNonnullFields("entranceTime", "departureTime")
                .verify();
    }
}