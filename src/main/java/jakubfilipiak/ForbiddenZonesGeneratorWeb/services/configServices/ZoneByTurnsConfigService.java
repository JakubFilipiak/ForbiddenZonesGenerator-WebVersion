package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.ZoneByTurnsConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.ZoneByTurnsConfigRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.ZoneByTurnsConfigValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 20.06.2019.
 */
@Service
public class ZoneByTurnsConfigService {

    private ZoneByTurnsConfigMapper configMapper;
    private ZoneByTurnsConfigRepository configRepository;

    public ZoneByTurnsConfigService(ZoneByTurnsConfigMapper configMapper,
                                    ZoneByTurnsConfigRepository configRepository) {
        this.configMapper = configMapper;
        this.configRepository = configRepository;
    }

    public List<ZoneByTurnsConfigDto> getConfigsDto() {
        return configRepository.findAllNotDeleted()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ZoneByTurnsConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public List<String> getVerifiedConfigsNames() {
        return configRepository.findAllNotDeletedAndVerified()
                .stream()
                .map(ZoneByTurnsConfig::getConfigName)
                .collect(Collectors.toList());
    }

    public boolean isConfigNameAlreadyInUse(String configName) {
        List<String> existingNames = getConfigsDto().stream()
                .map(ZoneByTurnsConfigDto::getConfigName)
                .collect(Collectors.toList());
        return existingNames.contains(configName);
    }

    public void addConfig(ZoneByTurnsConfigDto configDto) {
        ZoneByTurnsConfig config = configMapper.reverseMap(configDto);
        configRepository.save(config);
    }

    public void verifyConfig(String configName) {
        configRepository.findByConfigName(configName).ifPresent(config -> {
            ZoneByTurnsConfigValidator validator =
                    new ZoneByTurnsConfigValidator(config);
            if (validator.isEachParamPresent()) {
                config.setVerified(true);
                configRepository.save(config);
            }
        });
    }

    public void updateConfig(ZoneByTurnsConfigDto configDto) {
        configRepository.findByConfigName(configDto.getConfigName()).ifPresent(config -> {
            config.setMinTurnInitiationAngle(
                    configDto.getMinTurnInitiationAngle());
            config.setIgnoredTurnMinValue(
                    configDto.getIgnoredTurnMinValue());
            config.setIgnoredTurnMaxValue(
                    configDto.getIgnoredTurnMaxValue());
            config.setMinTurnsNumberInSeries(
                    configDto.getMinTurnsNumberInSeries());
            config.setMaxPausesNumberBetweenTurns(
                    configDto.getMaxPausesNumberBetweenTurns());
            config.setVerified(false);
            configRepository.save(config);
        });
    }

    public void setConfigAsDeleted(String configName) {
        configRepository.findByConfigName(configName).ifPresent(config -> {
            config.setDeleted(true);
            config.setConfigName(createDeprecatedName(configName));
            configRepository.save(config);
        });
    }

    private String createDeprecatedName(String configName) {
        final String DATE_FORMAT = "yyyy-MM-dd---HH-mm-ss-";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String localTimeNow = LocalDateTime.now().format(formatter);
        String prefix = "DEPRECATED-from-";
        return prefix + localTimeNow + configName;
    }
}
