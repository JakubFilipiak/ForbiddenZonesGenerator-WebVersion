package jakubfilipiak.ForbiddenZonesGeneratorWeb;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public enum Properties {

    INSTANCE;

    private int maxAllowedAngle = 10;


    private int ignoredTurnMinValue = 65;
    private int ignoredTurnMaxValue = 115;

    private int minTurnsNumberInSeries = 1;
    private int maxPauseOfTurns = 2;

    private boolean singleTurnZoneFullTime = true;
    private int singleTurnZoneBeginOffset = 2;
    private int singleTurnZoneEndOffset = 2;

    private boolean groupOfTurnsZoneFullTime = true;
    private int groupOfTurnsZoneBeginOffset = 2;
    private int groupOfTurnsZoneEndOffset = 2;

    private int minPointsNumberInSeries = 1;
    private int maxPauseOfPoints = 2;

    private boolean singlePointZoneFullTime = true;
    private int singlePointZoneBeginOffset = 2;
    private int singlePointZoneEndOffset = 2;

    private boolean groupOfPointsZoneFullTime = true;
    private int groupOfPointsZoneBeginOffset = 2;
    private int groupOfPointsZoneEndOffset = 2;

    public int getMaxAllowedAngle() {
        return maxAllowedAngle;
    }

    public int getMaxPauseOfTurns() {
        return maxPauseOfTurns;
    }

    public int getIgnoredTurnMinValue() {
        return ignoredTurnMinValue;
    }

    public int getIgnoredTurnMaxValue() {
        return ignoredTurnMaxValue;
    }

    public int getMinTurnsNumberInSeries() {
        return minTurnsNumberInSeries;
    }

    public boolean isSingleTurnZoneFullTime() {
        return singleTurnZoneFullTime;
    }

    public boolean isGroupOfTurnsZoneFullTime() {
        return groupOfTurnsZoneFullTime;
    }

    public int getSingleTurnZoneBeginOffset() {
        return singleTurnZoneBeginOffset;
    }

    public int getSingleTurnZoneEndOffset() {
        return singleTurnZoneEndOffset;
    }

    public int getGroupOfTurnsZoneBeginOffset() {
        return groupOfTurnsZoneBeginOffset;
    }

    public int getGroupOfTurnsZoneEndOffset() {
        return groupOfTurnsZoneEndOffset;
    }

    public int getMinPointsNumberInSeries() {
        return minPointsNumberInSeries;
    }

    public int getMaxPauseOfPoints() {
        return maxPauseOfPoints;
    }

    public boolean isSinglePointZoneFullTime() {
        return singlePointZoneFullTime;
    }

    public int getSinglePointZoneBeginOffset() {
        return singlePointZoneBeginOffset;
    }

    public int getSinglePointZoneEndOffset() {
        return singlePointZoneEndOffset;
    }

    public boolean isGroupOfPointsZoneFullTime() {
        return groupOfPointsZoneFullTime;
    }

    public int getGroupOfPointsZoneBeginOffset() {
        return groupOfPointsZoneBeginOffset;
    }

    public int getGroupOfPointsZoneEndOffset() {
        return groupOfPointsZoneEndOffset;
    }
}
