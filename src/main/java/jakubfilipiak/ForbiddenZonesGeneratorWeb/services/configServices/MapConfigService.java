package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.MapConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.MapConfigRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.MapConfigValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 13.06.2019.
 */
@Service
public class MapConfigService {

    private MapConfigMapper configMapper;
    private MapConfigRepository configRepository;

    public MapConfigService(MapConfigMapper configMapper,
                            MapConfigRepository configRepository) {
        this.configMapper = configMapper;
        this.configRepository = configRepository;
    }

    public void addConfig(MapConfigDto configDto, LocalFile localFile) {
        MapConfig config = configMapper.reverseMap(configDto);
        config.setMapFile(localFile);
        configRepository.save(config);
    }

    public void verifyConfig(String configName) {
        configRepository
                .findByConfigName(configName)
                .ifPresent(config -> {
                    MapConfigValidator validator = new MapConfigValidator(config);
                    if (validator.isEachCoordinateCorrect())
                        if (validator.isColorsDefinitionCorrect())
                            if (validator.isPNGMapCorrect()) {
                                config.setVerified(true);
                                configRepository.save(config);
                            }
                });
    }

    public List<MapConfigDto> getConfigsDto() {
        return configRepository
                .findAllNotDeleted()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<MapConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public void updateConfig(MapConfigDto configDto) {
        configRepository
                .findByConfigName(configDto.getConfigName())
                .ifPresent(config -> {
                    config.setAllowedRGBColor(
                            configDto.getAllowedRGBColor());
                    config.setForbiddenRGBColor(
                            configDto.getForbiddenRGBColor());
                    config.setBottomLeftCornerLatitude(
                            configDto.getBottomLeftCornerLatitude());
                    config.setBottomLeftCornerLongitude(
                            configDto.getBottomLeftCornerLongitude());
                    config.setUpperRightCornerLatitude(
                            configDto.getUpperRightCornerLatitude());
                    config.setUpperRightCornerLongitude(
                            configDto.getUpperRightCornerLongitude());
                    config.setVerified(false);
                    configRepository.save(config);
                });
    }

    public void setConfigAsDeleted(String configName) {
        configRepository
                .findByConfigName(configName)
                .ifPresent(config -> {
                    config.setDeleted(true);
                    config.setConfigName(createDeprecatedName(config.getConfigName()));
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
