package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.Properties;
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

    public boolean isTotalTurnAngleAllowed() {

        double totalAngle = TurnService.calculateTurnAngle(entranceTurn.getAngle()
                , departureTurn.getAngle());
        return totalAngle >= Properties.INSTANCE.getIgnoredTurnMinValue()
                && totalAngle <= Properties.INSTANCE.getIgnoredTurnMaxValue();
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
        return turnOfTrack.getAngle() > Properties.INSTANCE.getMaxAllowedAngle();
    }

    private boolean isForbiddenZoneStarted() {
        return entranceTurn != null;
    }

    private boolean isBufferReady() {

        boolean enoughTurns =
                turnsCounter >= Properties.INSTANCE.getMinTurnsNumberInSeries();
        boolean tooManyPauses =
                pauseCounter > Properties.INSTANCE.getMaxPauseOfTurns();
        boolean bufferReady =
                enoughTurns && tooManyPauses;

        if (!enoughTurns) {
            if (tooManyPauses) {
                cleanBuffer();
                return false;
            }
            return false;
        }
        return bufferReady;
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
