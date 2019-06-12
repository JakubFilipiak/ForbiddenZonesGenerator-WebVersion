package jakubfilipiak.ForbiddenZonesGeneratorWeb;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public enum ProcessingProperties {

    INSTANCE;

    // Turn

    @Getter @Setter private int minTurnInitiationAngle = 10;

    @Getter @Setter private int ignoredTurnMinValue = 65;
    @Getter @Setter private int ignoredTurnMaxValue = 115;

    // Zone forbidden by turns

    @Getter @Setter private int minTurnsNumberInSeries = 1;
    @Getter @Setter private int maxPausesNumberBetweenTurns = 2;

    @Getter @Setter private boolean singleTurnZoneFullTime = true;
    @Getter @Setter private int singleTurnZoneBeginOffset = 2;
    @Getter @Setter private int singleTurnZoneEndOffset = 2;

    @Getter @Setter private boolean groupOfTurnsZoneFullTime = true;
    @Getter @Setter private int groupOfTurnsZoneBeginOffset = 2;
    @Getter @Setter private int groupOfTurnsZoneEndOffset = 2;

    // Zone forbidden by points

    @Getter @Setter private boolean pointNeighborhoodVerification = true;
    @Getter @Setter private int radiusOfPixelsToBeVerified = 2;

    @Getter @Setter private int minPointsNumberInSeries = 1;
    @Getter @Setter private int maxPausesNumberBetweenPoints = 2;

    @Getter @Setter private int singlePointZoneBeginOffset = 2;
    @Getter @Setter private int singlePointZoneEndOffset = 2;

    @Getter @Setter private int groupOfPointsZoneBeginOffset = 2;
    @Getter @Setter private int groupOfPointsZoneEndOffset = 2;
}
