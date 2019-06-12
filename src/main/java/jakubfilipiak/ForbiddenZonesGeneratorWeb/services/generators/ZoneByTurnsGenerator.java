package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.ProcessingProperties;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.TurnOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.TurnService;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class ZoneByTurnsGenerator {

    private TurnOfTrack entranceTurn;
    private TurnOfTrack departureTurn;
    private int turnsCounter = 0;
    private int pauseCounter = 0;

    public boolean updateTurnsBuffer(TurnOfTrack turnOfTrack) {
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
        return isBufferReady();
    }

    public ForbiddenZone createZoneFromBuffer() {

        ForbiddenZone forbiddenZone;
        if (isOnlyOneTurn()) {
            forbiddenZone = ForbiddenZone.fromSingleTurn(entranceTurn);
        } else {
            forbiddenZone = ForbiddenZone.fromGroupOfTurns(entranceTurn, departureTurn);
        }
        cleanBuffer();
        return forbiddenZone;
    }

    private boolean isTurnTakenIntoConsideration(TurnOfTrack turnOfTrack) {
        return turnOfTrack.getAngle() >= ProcessingProperties.INSTANCE.getMinTurnInitiationAngle();
    }

    private boolean isForbiddenZoneStarted() {
        return entranceTurn != null;
    }

    private boolean isBufferReady() {

        boolean enoughTurns =
                turnsCounter >= ProcessingProperties.INSTANCE.getMinTurnsNumberInSeries();
        boolean tooManyPauses =
                pauseCounter > ProcessingProperties.INSTANCE.getMaxPausesNumberBetweenTurns();
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
            if (!isTotalTurnAngleAllowed()) {
                return true;
            } else {
                cleanBuffer();
                return false;
            }
        }
        return false;
    }

    private boolean isTotalTurnAngleAllowed() {

        double totalAngle = TurnService.calculateTurnAngle(entranceTurn.getAbsoluteEntranceAngle(), departureTurn.getAbsoluteDepartureAngle());
        return totalAngle >= ProcessingProperties.INSTANCE.getIgnoredTurnMinValue()
                && totalAngle <= ProcessingProperties.INSTANCE.getIgnoredTurnMaxValue();
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
