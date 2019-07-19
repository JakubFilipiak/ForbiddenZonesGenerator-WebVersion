package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.ZoneByPointsConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.ZoneByPointsConfigRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.ZoneByPointsConfigValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 21.06.2019.
 */
@Service
public class ZoneByPointsConfigService {

    private ZoneByPointsConfigMapper configMapper;
    private ZoneByPointsConfigRepository configRepository;

    public ZoneByPointsConfigService(ZoneByPointsConfigMapper configMapper, ZoneByPointsConfigRepository configRepository) {
        this.configMapper = configMapper;
        this.configRepository = configRepository;
    }

    public void addConfig(ZoneByPointsConfigDto configDto) {
        ZoneByPointsConfig config = configMapper.reverseMap(configDto);
        configRepository.save(config);
    }

    public void verifyConfig(String configName) {
        configRepository
                .findByConfigName(configName)
                .ifPresent(config -> {
                    ZoneByPointsConfigValidator validator =
                            new ZoneByPointsConfigValidator(config);
                    if (validator.isEachParamPresent()) {
                        config.setVerified(true);
                        configRepository.save(config);
                    }
                });
    }

    public List<ZoneByPointsConfigDto> getConfigsDto() {
        return configRepository
                .findAllNotDeleted()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ZoneByPointsConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public void updateConfig(ZoneByPointsConfigDto configDto) {
        configRepository
                .findByConfigName(configDto.getConfigName())
                .ifPresent(config -> {
                    config.setPointsMultiplication(
                            configDto.isPointsMultiplication());
                    config.setPointNeighborhoodVerification(
                            configDto.isPointNeighborhoodVerification());
                    config.setRadiusOfPixelsToBeVerified(
                            configDto.getRadiusOfPixelsToBeVerified());
                    config.setMinPointsNumberInSeries(
                            configDto.getMinPointsNumberInSeries());
                    config.setMaxPausesNumberBetweenPoints(
                            configDto.getMaxPausesNumberBetweenPoints());
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
                .map(ZoneByPointsConfig::getConfigName)
                .collect(Collectors.toList());
    }

    public boolean isConfigNameAlreadyInUse(String configName) {
        List<String> existingNames = getConfigsDto().stream()
                .map(ZoneByPointsConfigDto::getConfigName)
                .collect(Collectors.toList());
        return existingNames.contains(configName);
    }
}
