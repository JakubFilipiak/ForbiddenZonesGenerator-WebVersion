package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;

/**
 * Created by Jakub Filipiak on 03.07.2019.
 */
public class ZoneByPointsConfigValidator {

    private ZoneByPointsConfig config;

    public ZoneByPointsConfigValidator(ZoneByPointsConfig config) {
        this.config = config;
    }

    public boolean isEachParamPresent() {
        return isPointNeighborhoodVerificationParamPresent() &&
                isNumberOfTurnsAndPausesPresent();
    }

    private boolean isPointNeighborhoodVerificationParamPresent() {
        if (config.isPointNeighborhoodVerification()) {
            return config.getRadiusOfPixelsToBeVerified() != 0;
        }
        return true;
    }

    private boolean isNumberOfTurnsAndPausesPresent() {
        return config.getMinPointsNumberInSeries() != -1 &&
                config.getMaxPausesNumberBetweenPoints() != -1;
    }
}
