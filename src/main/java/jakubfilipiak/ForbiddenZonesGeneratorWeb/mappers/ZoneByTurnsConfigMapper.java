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
        ZoneByTurnsConfigDto dto = ZoneByTurnsConfigDto
                .builder()
                .configName(dao.getConfigName())
                .minTurnInitiationAngle(dao.getMinTurnInitiationAngle())
                .ignoreTurns(dao.isIgnoreTurns())
                .minTurnsNumberInSeries(dao.getMinTurnsNumberInSeries())
                .maxPausesNumberBetweenTurns(dao.getMaxPausesNumberBetweenTurns())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();
        if (dao.isIgnoreTurns()) {
            dto.setIgnoredTurnMinValue(dao.getIgnoredTurnMinValue());
            dto.setIgnoredTurnMaxValue(dao.getIgnoredTurnMaxValue());
        }
        return dto;
    }

    @Override
    public ZoneByTurnsConfig reverseMap(ZoneByTurnsConfigDto dto) {
        ZoneByTurnsConfig dao = ZoneByTurnsConfig
                .builder()
                .configName(dto.getConfigName())
                .minTurnInitiationAngle(dto.getMinTurnInitiationAngle())
                .ignoreTurns(dto.isIgnoreTurns())
                .minTurnsNumberInSeries(dto.getMinTurnsNumberInSeries())
                .maxPausesNumberBetweenTurns(dto.getMaxPausesNumberBetweenTurns())
                .build();
        if (dto.isIgnoreTurns()) {
            dao.setIgnoredTurnMinValue(dto.getIgnoredTurnMinValue());
            dao.setIgnoredTurnMaxValue(dto.getIgnoredTurnMaxValue());
        }
        return dao;
    }
}
