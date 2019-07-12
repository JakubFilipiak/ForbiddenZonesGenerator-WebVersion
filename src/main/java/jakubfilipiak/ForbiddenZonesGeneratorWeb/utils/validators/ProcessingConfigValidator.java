package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ProcessingConfig;

/**
 * Created by Jakub Filipiak on 12.07.2019.
 */
public class ProcessingConfigValidator {

    private ProcessingConfig config;

    public ProcessingConfigValidator(ProcessingConfig config) {
        this.config = config;
    }

    public boolean isAtLeastOneParamTrue() {
        return config.isZoneByDropTimeCreation() ||
                config.isZoneByPointsCreation() ||
                config.isZoneByTurnsCreation();
    }
}
