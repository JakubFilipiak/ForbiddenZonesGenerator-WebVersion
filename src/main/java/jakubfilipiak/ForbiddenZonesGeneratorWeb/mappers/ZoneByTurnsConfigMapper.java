package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 19.06.2019.
 */
@Component
public class ZoneByTurnsConfigMapper implements Mapper<ZoneByTurnsConfig,
        ZoneByTurnsConfigDto> {

    @Override
    public ZoneByTurnsConfigDto map(ZoneByTurnsConfig dao) {
        return ZoneByTurnsConfigDto
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
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();
    }

    @Override
    public ZoneByTurnsConfig reverseMap(ZoneByTurnsConfigDto dto) {
        return ZoneByTurnsConfig
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
                .build();
    }
}
