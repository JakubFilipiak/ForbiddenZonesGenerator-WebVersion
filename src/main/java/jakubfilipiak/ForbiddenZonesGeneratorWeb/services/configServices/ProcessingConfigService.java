package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.ProcessingConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ProcessingConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ProcessingConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.ProcessingConfigRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.ProcessingConfigValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Service
public class ProcessingConfigService {

    private ProcessingConfigRepository configRepository;
    private ProcessingConfigMapper configMapper;

    public ProcessingConfigService(ProcessingConfigRepository configRepository,
                                   ProcessingConfigMapper configMapper) {
        this.configRepository = configRepository;
        this.configMapper = configMapper;
    }

    public void addConfig(ProcessingConfigDto configDto) {
        ProcessingConfig config = configMapper.reverseMap(configDto);
        configRepository.save(config);
    }

    public void verifyConfig(String configName) {
        configRepository
                .findByConfigName(configName)
                .ifPresent(config -> {
                    ProcessingConfigValidator validator =
                            new ProcessingConfigValidator(config);
                    if (validator.isAtLeastOneParamTrue()) {
                        config.setVerified(true);
                        configRepository.save(config);
                    }
                });
    }

    public List<ProcessingConfigDto> getConfigsDto() {
        return configRepository
                .findAllNotDeleted()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ProcessingConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public void updateConfig(ProcessingConfigDto configDto) {
        configRepository
                .findByConfigName(configDto.getConfigName())
                .ifPresent(config -> {
                    config.setZoneByDropTimeCreation(
                            configDto.isZoneByDropTimeCreation());
                    config.setZoneByPointsCreation
                            (configDto.isZoneByPointsCreation());
                    config.setZoneByTurnsCreation(
                            configDto.isZoneByTurnsCreation());
                    config.setVerified(false);
                    configRepository.save(config);
                });
    }

    public void setConfigAsDeleted(String configName) {
        configRepository
                .findByConfigName(configName)
                .ifPresent(config -> {
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

    public List<String> getVerifiedConfigsNames() {
        return configRepository
                .findAllNotDeletedAndVerified()
                .stream()
                .map(ProcessingConfig::getConfigName)
                .collect(Collectors.toList());
    }
}
