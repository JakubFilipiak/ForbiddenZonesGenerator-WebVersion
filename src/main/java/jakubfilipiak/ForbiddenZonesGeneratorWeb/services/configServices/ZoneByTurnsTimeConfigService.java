package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.ZoneByTurnsTimeConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsTimeConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsTimeConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.ZoneByTurnsTimeConfigRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.ZoneByTurnsTimeConfigValidator;
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
public class ZoneByTurnsTimeConfigService {

    private ZoneByTurnsTimeConfigMapper configMapper;
    private ZoneByTurnsTimeConfigRepository configRepository;

    public ZoneByTurnsTimeConfigService(ZoneByTurnsTimeConfigMapper configMapper,
                                        ZoneByTurnsTimeConfigRepository configRepository) {
        this.configMapper = configMapper;
        this.configRepository = configRepository;
    }

    public List<ZoneByTurnsTimeConfigDto> getConfigsDto() {
        return configRepository.findAllNotDeleted()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<ZoneByTurnsTimeConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public List<String> getVerifiedConfigsNames() {
        return configRepository.findAllNotDeletedAndVerified()
                .stream()
                .map(ZoneByTurnsTimeConfig::getConfigName)
                .collect(Collectors.toList());
    }

    public boolean isConfigNameAlreadyInUse(String configName) {
        List<String> existingNames = getConfigsDto().stream()
                .map(ZoneByTurnsTimeConfigDto::getConfigName)
                .collect(Collectors.toList());
        return existingNames.contains(configName);
    }

    public void addConfig(ZoneByTurnsTimeConfigDto configDto) {
        ZoneByTurnsTimeConfig config = configMapper.reverseMap(configDto);
        configRepository.save(config);
    }

    public void verifyConfig(String configName) {
        configRepository.findByConfigName(configName).ifPresent(config -> {
            ZoneByTurnsTimeConfigValidator validator =
                    new ZoneByTurnsTimeConfigValidator(config);
            if (validator.isCorrect()) {
                config.setVerified(true);
                configRepository.save(config);
            }
        });
    }

    public void updateConfig(ZoneByTurnsTimeConfigDto configDto) {
        configRepository.findByConfigName(configDto.getConfigName()).ifPresent(config -> {
            config.setSingleTurnZoneFullTime(
                    configDto.isSingleTurnZoneFullTime());
            config.setSingleTurnZoneBeginOffset(
                    configDto.getSingleTurnZoneBeginOffset());
            config.setSingleTurnZoneEndOffset(
                    configDto.getSingleTurnZoneEndOffset());
            config.setGroupOfTurnsZoneFullTime(
                    configDto.isGroupOfTurnsZoneFullTime());
            config.setGroupOfTurnsZoneBeginOffset(
                    configDto.getGroupOfTurnsZoneBeginOffset());
            config.setGroupOfTurnsZoneEndOffset(
                    configDto.getGroupOfTurnsZoneEndOffset());
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
