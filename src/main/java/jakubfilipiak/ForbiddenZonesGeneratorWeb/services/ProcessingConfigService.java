package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.ProcessingConfigMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.ProcessingConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.ProcessingConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.ProcessingConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Service
public class ProcessingConfigService {

    private ProcessingConfigRepository configRepository;
    private ProcessingConfigMapper configMapper;

    public ProcessingConfigService(ProcessingConfigRepository configRepository, ProcessingConfigMapper configMapper) {
        this.configRepository = configRepository;
        this.configMapper = configMapper;
    }

    public ProcessingConfig addConfig(ProcessingConfigDto configDto) {
        return configRepository.save(configMapper.reverseMap(configDto));
    }

    public List<ProcessingConfig> getConfigs() {
        return configRepository.findAll();
    }

    public void updateConfig(ProcessingConfigDto configDto) {
        configRepository
                .findByConfigName(configDto.getConfigName())
                .ifPresent(config -> {
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
                    config.setPointNeighborhoodVerification(
                            configDto.isPointNeighborhoodVerification());
                    config.setRadiusOfPixelsToBeVerified(
                            configDto.getRadiusOfPixelsToBeVerified());
                    config.setMinPointsNumberInSeries(
                            configDto.getMinPointsNumberInSeries());
                    config.setMaxPausesNumberBetweenPoints(
                            configDto.getMaxPausesNumberBetweenPoints());
                    config.setSinglePointZoneBeginOffset(
                            configDto.getSinglePointZoneBeginOffset());
                    config.setSinglePointZoneEndOffset(
                            configDto.getSinglePointZoneEndOffset());
                    config.setGroupOfPointsZoneBeginOffset(
                            configDto.getGroupOfPointsZoneBeginOffset());
                    config.setGroupOfPointsZoneEndOffset(
                            configDto.getGroupOfPointsZoneEndOffset());

                    configRepository.save(config);
                });
    }

    public void deleteConfig(String configName) {
        configRepository.deleteByConfigName(configName);
    }
}
