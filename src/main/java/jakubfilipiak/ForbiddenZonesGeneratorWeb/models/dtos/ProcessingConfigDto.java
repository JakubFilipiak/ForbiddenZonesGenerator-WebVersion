package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Getter
@Setter
@Builder
public class ProcessingConfigDto {

    private String configName;

    // Turn

    private int minTurnInitiationAngle;

    private int ignoredTurnMinValue;
    private int ignoredTurnMaxValue;

    // Zone forbidden by turns

    private int minTurnsNumberInSeries;
    private int maxPausesNumberBetweenTurns;

    private boolean singleTurnZoneFullTime;
    private int singleTurnZoneBeginOffset;
    private int singleTurnZoneEndOffset;

    private boolean groupOfTurnsZoneFullTime;
    private int groupOfTurnsZoneBeginOffset;
    private int groupOfTurnsZoneEndOffset;

    // Zone forbidden by points

    private boolean pointNeighborhoodVerification;
    private int radiusOfPixelsToBeVerified;

    private int minPointsNumberInSeries;
    private int maxPausesNumberBetweenPoints;

    private int singlePointZoneBeginOffset;
    private int singlePointZoneEndOffset;

    private int groupOfPointsZoneBeginOffset;
    private int groupOfPointsZoneEndOffset;
}
