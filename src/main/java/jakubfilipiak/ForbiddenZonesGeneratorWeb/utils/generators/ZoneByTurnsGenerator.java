package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsTimeConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TurnOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.TurnAngleCalculator;

import java.util.Optional;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class ZoneByTurnsGenerator {

    private TurnAngleCalculator turnAngleCalculator;

    private ZoneByTurnsConfig config;
    private ZoneByTurnsTimeConfig timeConfig;

    private int minTurnsNumberInSeries;
    private int minTurnInitiationAngle;
    private int maxPausesNumberBetweenTurns;
    private boolean ignoreTurns;
    private int ignoredTurnMinValue;
    private int ignoredTurnMaxValue;

    private TurnOfTrack entranceTurn;
    private TurnOfTrack departureTurn;
    private int turnsCounter = 0;
    private int pauseCounter = 0;

    public ZoneByTurnsGenerator(ZoneByTurnsConfig config,
                                ZoneByTurnsTimeConfig timeConfig,
                                TurnAngleCalculator turnAngleCalculator) {
        this.config = config;
        this.timeConfig = timeConfig;
        this.turnAngleCalculator = turnAngleCalculator;
        initConfig();
    }

    private void initConfig() {
        this.minTurnsNumberInSeries = config.getMinTurnsNumberInSeries();
        this.minTurnInitiationAngle = config.getMinTurnInitiationAngle();
        this.maxPausesNumberBetweenTurns = config.getMaxPausesNumberBetweenTurns();
        this.ignoreTurns = config.isIgnoreTurns();
        if (config.isIgnoreTurns()) {
            this.ignoredTurnMinValue = config.getIgnoredTurnMinValue();
            this.ignoredTurnMaxValue = config.getIgnoredTurnMaxValue();
        }
    }

    public void updateTurnsBuffer(TurnOfTrack turnOfTrack) {
        if (isTurnTakenIntoConsideration(turnOfTrack)) {
            if (!isForbiddenZoneStarted()) {
                entranceTurn = turnOfTrack;
                departureTurn = turnOfTrack;
                pauseCounter = 0;
            } else {
                departureTurn = turnOfTrack;
            }
            turnsCounter++;
        } else {
            pauseCounter++;
        }
    }

    public Optional<ForbiddenZone> createZoneFromBuffer() {
        if (isBufferReady()) {
            ForbiddenZone forbiddenZone;
            if (isOnlyOneTurn()) {
                forbiddenZone = ForbiddenZone.fromSingleTurn(entranceTurn, timeConfig);
            } else {
                forbiddenZone = ForbiddenZone.fromGroupOfTurns(entranceTurn,
                        departureTurn, timeConfig);
            }
            cleanBuffer();
            return Optional.of(forbiddenZone);
        }
        return Optional.empty();
    }

    public Optional<ForbiddenZone> createPossibleZoneFromRemainingData() {
        boolean enoughTurns = turnsCounter >= minTurnsNumberInSeries;
        if (enoughTurns) {
            ForbiddenZone forbiddenZone;
            if (isOnlyOneTurn()) {
                forbiddenZone = ForbiddenZone.fromSingleTurn(entranceTurn, timeConfig);
            } else {
                forbiddenZone = ForbiddenZone.fromGroupOfTurns(entranceTurn,
                        departureTurn, timeConfig);
            }
            cleanBuffer();
            return Optional.of(forbiddenZone);
        }
        cleanBuffer();
        return Optional.empty();
    }

    private boolean isTurnTakenIntoConsideration(TurnOfTrack turnOfTrack) {
        System.out.println(turnOfTrack.getAngle() + " vs " + minTurnInitiationAngle);
        return turnOfTrack.getAngle() >= minTurnInitiationAngle;
    }

    private boolean isForbiddenZoneStarted() {
        return entranceTurn != null;
    }

    private boolean isBufferReady() {
        boolean enoughTurns =
                turnsCounter >= minTurnsNumberInSeries;
        boolean tooManyPauses =
                pauseCounter > maxPausesNumberBetweenTurns;
        boolean dataReady =
                enoughTurns && tooManyPauses;

        if (!enoughTurns) {
            if (tooManyPauses) {
                cleanBuffer();
                return false;
            }
            return false;
        }
        if (dataReady) {
            if (isTotalTurnAngleForbidden()) {
                return true;
            } else {
                cleanBuffer();
                return false;
            }
        }
        return false;
    }

    private boolean isTotalTurnAngleForbidden() {
        if (ignoreTurns) {
            double totalAngle = turnAngleCalculator.calculateTurnAngle(
                    entranceTurn.getAbsoluteEntranceAngle(),
                    departureTurn.getAbsoluteDepartureAngle());
            return totalAngle >= ignoredTurnMinValue
                    && totalAngle <= ignoredTurnMaxValue;
        }
        return true;
    }

    private boolean isOnlyOneTurn() {
        return turnsCounter == 1;
    }

    private void cleanBuffer() {
        entranceTurn = null;
        departureTurn = null;
        turnsCounter = 0;
        pauseCounter = 0;
    }
}
