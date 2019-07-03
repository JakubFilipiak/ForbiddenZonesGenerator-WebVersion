package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;

/**
 * Created by Jakub Filipiak on 03.07.2019.
 */
public class ZoneByTurnsConfigValidator {

    private ZoneByTurnsConfig config;

    public ZoneByTurnsConfigValidator(ZoneByTurnsConfig config) {
        this.config = config;
    }

    public boolean isEachParamPresent() {
        return isMinTurnInitiationAnglePresent() &&
                isIgnoreTurnsParamsPresent() &&
                isNumberOfTurnsAndPausesPresent();
    }

    private boolean isMinTurnInitiationAnglePresent() {
        return config.getMinTurnInitiationAngle() != 0;
    }

    private boolean isIgnoreTurnsParamsPresent() {
        if (config.isIgnoreTurns()) {
            return config.getIgnoredTurnMinValue() != 0 &&
                    config.getIgnoredTurnMaxValue() != 0;
        }
        return true;
    }

    private boolean isNumberOfTurnsAndPausesPresent() {
        return config.getMinTurnsNumberInSeries() != -1 &&
                config.getMaxPausesNumberBetweenTurns() != -1;
    }
}
