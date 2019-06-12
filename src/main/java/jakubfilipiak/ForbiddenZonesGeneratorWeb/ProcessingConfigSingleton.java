package jakubfilipiak.ForbiddenZonesGeneratorWeb;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public enum ProcessingConfigSingleton {

    INSTANCE;

    // Turn

    @Getter @Setter private int minTurnInitiationAngle;

    @Getter @Setter private int ignoredTurnMinValue;
    @Getter @Setter private int ignoredTurnMaxValue;

    // Zone forbidden by turns

    @Getter @Setter private int minTurnsNumberInSeries;
    @Getter @Setter private int maxPausesNumberBetweenTurns;

    @Getter @Setter private boolean singleTurnZoneFullTime;
    @Getter @Setter private int singleTurnZoneBeginOffset;
    @Getter @Setter private int singleTurnZoneEndOffset;

    @Getter @Setter private boolean groupOfTurnsZoneFullTime;
    @Getter @Setter private int groupOfTurnsZoneBeginOffset;
    @Getter @Setter private int groupOfTurnsZoneEndOffset;

    // Zone forbidden by points

    @Getter @Setter private boolean pointNeighborhoodVerification;
    @Getter @Setter private int radiusOfPixelsToBeVerified;

    @Getter @Setter private int minPointsNumberInSeries;
    @Getter @Setter private int maxPausesNumberBetweenPoints;

    @Getter @Setter private int singlePointZoneBeginOffset;
    @Getter @Setter private int singlePointZoneEndOffset;

    @Getter @Setter private int groupOfPointsZoneBeginOffset;
    @Getter @Setter private int groupOfPointsZoneEndOffset;
}
