package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsTimeConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsTimeConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 14.07.2019.
 */
@Component
public class ZoneByPointsTimeConfigMapper implements Mapper<ZoneByPointsTimeConfig, ZoneByPointsTimeConfigDto> {

    @Override
    public ZoneByPointsTimeConfigDto map(ZoneByPointsTimeConfig dao) {
        return ZoneByPointsTimeConfigDto.builder()
                .configName(dao.getConfigName())
                .singlePointZoneBeginOffset(dao.getSinglePointZoneBeginOffset())
                .singlePointZoneEndOffset(dao.getSinglePointZoneEndOffset())
                .groupOfPointsZoneBeginOffset(dao.getGroupOfPointsZoneBeginOffset())
                .groupOfPointsZoneEndOffset(dao.getGroupOfPointsZoneEndOffset())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();
    }

    @Override
    public ZoneByPointsTimeConfig reverseMap(ZoneByPointsTimeConfigDto dto) {
        return ZoneByPointsTimeConfig.builder()
                .configName(dto.getConfigName())
                .singlePointZoneBeginOffset(dto.getSinglePointZoneBeginOffset())
                .singlePointZoneEndOffset(dto.getSinglePointZoneEndOffset())
                .groupOfPointsZoneBeginOffset(dto.getGroupOfPointsZoneBeginOffset())
                .groupOfPointsZoneEndOffset(dto.getGroupOfPointsZoneEndOffset())
                .build();
    }
}
