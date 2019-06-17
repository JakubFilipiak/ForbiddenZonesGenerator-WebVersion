package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.MapConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.MapConfigRepository;
import org.springframework.stereotype.Service;

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
    private ColorService colorService;

    public MapConfigService(MapConfigMapper configMapper, MapConfigRepository configRepository, ColorService colorService) {
        this.configMapper = configMapper;
        this.configRepository = configRepository;
        this.colorService = colorService;
    }

    public MapConfig addConfig(MapConfigDto configDto, LocalFile localFile) {
        MapConfig config = configMapper.reverseMap(configDto);
        config.setFilename(localFile.getOriginalName());
        config.setFilePathname(localFile.getUniquePathname());
        return configRepository.save(config);
    }

    public Optional<MapConfig> getConfigByConfigName(String configName) {
        return configRepository.findByConfigName(configName);
    }

    public List<MapConfigDto> getConfigsDto() {
        return configRepository
                .findAll()
                .stream()
                .map(configMapper::map)
                .collect(Collectors.toList());
    }

    public void updateConfig(MapConfigDto configDto) {
        configRepository
                .findByConfigName(configDto.getConfigName())
                .ifPresent(config -> {
                    config.setAllowedRGBColor(configDto.getAllowedRGBColor());
                    config.setForbiddenRGBColor(configDto.getForbiddenRGBColor());
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

    public void deleteConfig(String configName) {
        configRepository.deleteByConfigName(configName);
    }
}
