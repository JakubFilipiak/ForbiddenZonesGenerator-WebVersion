package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsTimeConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsTimeConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 14.07.2019.
 */
@Component
public class ZoneByTurnsTimeConfigMapper implements Mapper<ZoneByTurnsTimeConfig,
        ZoneByTurnsTimeConfigDto> {

    @Override
    public ZoneByTurnsTimeConfigDto map(ZoneByTurnsTimeConfig dao) {
        return ZoneByTurnsTimeConfigDto
                .builder()
                .configName(dao.getConfigName())
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
    public ZoneByTurnsTimeConfig reverseMap(ZoneByTurnsTimeConfigDto dto) {
        return ZoneByTurnsTimeConfig
                .builder()
                .configName(dto.getConfigName())
                .singleTurnZoneFullTime(dto.isSingleTurnZoneFullTime())
                .singleTurnZoneBeginOffset(dto.getSingleTurnZoneBeginOffset())
                .singleTurnZoneEndOffset(dto.getSingleTurnZoneEndOffset())
                .groupOfTurnsZoneFullTime(dto.isGroupOfTurnsZoneFullTime())
                .groupOfTurnsZoneBeginOffset(dto.getGroupOfTurnsZoneBeginOffset())
                .groupOfTurnsZoneEndOffset(dto.getGroupOfTurnsZoneEndOffset())
                .build();
    }
}
