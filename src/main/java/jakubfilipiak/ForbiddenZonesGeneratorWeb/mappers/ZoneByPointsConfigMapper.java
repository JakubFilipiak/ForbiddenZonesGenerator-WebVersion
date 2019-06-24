package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 19.06.2019.
 */
@Component
public class ZoneByPointsConfigMapper implements Mapper<ZoneByPointsConfig,
        ZoneByPointsConfigDto> {

    @Override
    public ZoneByPointsConfigDto map(ZoneByPointsConfig dao) {
        ZoneByPointsConfigDto dto = ZoneByPointsConfigDto
                .builder()
                .configName(dao.getConfigName())
                .pointsMultiplication(dao.isPointsMultiplication())
                .pointNeighborhoodVerification(dao.isPointNeighborhoodVerification())
                .minPointsNumberInSeries(dao.getMinPointsNumberInSeries())
                .maxPausesNumberBetweenPoints(dao.getMaxPausesNumberBetweenPoints())
                .singlePointZoneBeginOffset(dao.getSinglePointZoneBeginOffset())
                .singlePointZoneEndOffset(dao.getSinglePointZoneEndOffset())
                .groupOfPointsZoneBeginOffset(dao.getGroupOfPointsZoneBeginOffset())
                .groupOfPointsZoneEndOffset(dao.getGroupOfPointsZoneEndOffset())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();

        if (dao.isPointsMultiplication()) {
            dto.setPointsDivider(dao.getPointsDivider());
        }
        if (dao.isPointNeighborhoodVerification()) {
            dto.setRadiusOfPixelsToBeVerified(dao.getRadiusOfPixelsToBeVerified());
        }
        return dto;
    }

    @Override
    public ZoneByPointsConfig reverseMap(ZoneByPointsConfigDto dto) {
        return ZoneByPointsConfig
                .builder()
                .configName(dto.getConfigName())
                .pointsMultiplication(dto.isPointsMultiplication())
                .pointsDivider(dto.getPointsDivider())
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