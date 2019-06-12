package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.ProcessingConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.ProcessingConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Component
public class ProcessingConfigMapper implements Mapper<ProcessingConfig, ProcessingConfigDto> {

    @Override
    public ProcessingConfigDto map(ProcessingConfig dao) {
        return ProcessingConfigDto
                .builder()
                .configName(dao.getConfigName())
                .minTurnInitiationAngle(dao.getMinTurnInitiationAngle())
                .ignoredTurnMinValue(dao.getIgnoredTurnMinValue())
                .ignoredTurnMaxValue(dao.getIgnoredTurnMaxValue())
                .minTurnsNumberInSeries(dao.getMinTurnsNumberInSeries())
                .maxPausesNumberBetweenTurns(dao.getMaxPausesNumberBetweenTurns())
                .singleTurnZoneFullTime(dao.isSingleTurnZoneFullTime())
                .singleTurnZoneBeginOffset(dao.getSingleTurnZoneBeginOffset())
                .singleTurnZoneEndOffset(dao.getSingleTurnZoneEndOffset())
                .groupOfTurnsZoneFullTime(dao.isGroupOfTurnsZoneFullTime())
                .groupOfTurnsZoneBeginOffset(dao.getGroupOfTurnsZoneBeginOffset())
                .groupOfTurnsZoneEndOffset(dao.getGroupOfTurnsZoneEndOffset())
                .pointNeighborhoodVerification(dao.isPointNeighborhoodVerification())
                .radiusOfPixelsToBeVerified(dao.getRadiusOfPixelsToBeVerified())
                .minPointsNumberInSeries(dao.getMinPointsNumberInSeries())
                .maxPausesNumberBetweenPoints(dao.getMaxPausesNumberBetweenPoints())
                .singlePointZoneBeginOffset(dao.getSinglePointZoneBeginOffset())
                .singlePointZoneEndOffset(dao.getSinglePointZoneEndOffset())
                .groupOfPointsZoneBeginOffset(dao.getGroupOfPointsZoneBeginOffset())
                .groupOfPointsZoneEndOffset(dao.getGroupOfPointsZoneEndOffset())
                .build();
    }

    @Override
    public ProcessingConfig reverseMap(ProcessingConfigDto dto) {
        return ProcessingConfig
                .builder()
                .configName(dto.getConfigName())
                .minTurnInitiationAngle(dto.getMinTurnInitiationAngle())
                .ignoredTurnMinValue(dto.getIgnoredTurnMinValue())
                .ignoredTurnMaxValue(dto.getIgnoredTurnMaxValue())
                .minTurnsNumberInSeries(dto.getMinTurnsNumberInSeries())
                .maxPausesNumberBetweenTurns(dto.getMaxPausesNumberBetweenTurns())
                .singleTurnZoneFullTime(dto.isSingleTurnZoneFullTime())
                .singleTurnZoneBeginOffset(dto.getSingleTurnZoneBeginOffset())
                .singleTurnZoneEndOffset(dto.getSingleTurnZoneEndOffset())
                .groupOfTurnsZoneFullTime(dto.isGroupOfTurnsZoneFullTime())
                .groupOfTurnsZoneBeginOffset(dto.getGroupOfTurnsZoneBeginOffset())
                .groupOfTurnsZoneEndOffset(dto.getGroupOfTurnsZoneEndOffset())
                .pointNeighborhoodVerification(dto.isPointNeighborhoodVerification())
                .radiusOfPixelsToBeVerified(dto.getRadiusOfPixelsToBeVerified())
                .minPointsNumberInSeries(dto.getMinPointsNumberInSeries())
                .maxPausesNumberBetweenPoints(dto.getMaxPausesNumberBetweenPoints())
                .singlePointZoneBeginOffset(dto.getSinglePointZoneBeginOffset())
                .singlePointZoneEndOffset(dto.getSinglePointZoneEndOffset())
                .groupOfPointsZoneBeginOffset(dto.getGroupOfPointsZoneBeginOffset())
                .groupOfPointsZoneEndOffset(dto.getGroupOfPointsZoneEndOffset())
                .build();
    }
}
