package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.ZoneByPointsTimeConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsTimeConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsTimeConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.ZoneByPointsTimeConfigRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.ZoneByPointsTimeConfigValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 14.07.2019.
 */
@Service
public class ZoneByPointsTimeConfigService {

    private ZoneByPointsTimeConfigMapper configMapper;
    private ZoneByPointsTimeConfigRepository configRepository;

    public ZoneByPointsTimeConfigService(ZoneByPointsTimeConfigMapper configMapper,
                                         ZoneByPointsTimeConfigRepository configRepository) {
        this.configMapper = configMapper;
        this.configRepository = configRepository;
    }

    public List<ZoneByPointsTimeConfigDto> getConfigsDto() {
        return configRepository.findAllNotDeleted()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ZoneByPointsTimeConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public List<String> getVerifiedConfigsNames() {
        return configRepository.findAllNotDeletedAndVerified()
                .stream()
                .map(ZoneByPointsTimeConfig::getConfigName)
                .collect(Collectors.toList());
    }

    public boolean isConfigNameAlreadyInUse(String configName) {
        List<String> existingNames = getConfigsDto().stream()
                .map(ZoneByPointsTimeConfigDto::getConfigName)
                .collect(Collectors.toList());
        return existingNames.contains(configName);
    }

    public void addConfig(ZoneByPointsTimeConfigDto configDto) {
        ZoneByPointsTimeConfig config = configMapper.reverseMap(configDto);
        configRepository.save(config);
    }

    public void verifyConfig(String configName) {
        configRepository.findByConfigName(configName).ifPresent(config -> {
            ZoneByPointsTimeConfigValidator validator =
                    new ZoneByPointsTimeConfigValidator(config);
            if (validator.isCorrect()) {
                config.setVerified(true);
                configRepository.save(config);
            }
        });
    }

    public void updateConfig(ZoneByPointsTimeConfigDto configDto) {
        configRepository.findByConfigName(configDto.getConfigName()).ifPresent(config -> {
            config.setSinglePointZoneBeginOffset(
                    configDto.getSinglePointZoneBeginOffset());
            config.setSinglePointZoneEndOffset(
                    configDto.getSinglePointZoneEndOffset());
            config.setGroupOfPointsZoneBeginOffset(
                    configDto.getGroupOfPointsZoneBeginOffset());
            config.setGroupOfPointsZoneEndOffset(
                    configDto.getGroupOfPointsZoneEndOffset());
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
