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
        ZoneByPointsConfigDto dto = ZoneByPointsConfigDto.builder()
                .configName(dao.getConfigName())
                .pointsMultiplication(dao.isPointsMultiplication())
                .pointNeighborhoodVerification(dao.isPointNeighborhoodVerification())
                .minPointsNumberInSeries(dao.getMinPointsNumberInSeries())
                .maxPausesNumberBetweenPoints(dao.getMaxPausesNumberBetweenPoints())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();
        if (dao.isPointNeighborhoodVerification()) {
            dto.setRadiusOfPixelsToBeVerified(dao.getRadiusOfPixelsToBeVerified());
        }
        return dto;
    }

    @Override
    public ZoneByPointsConfig reverseMap(ZoneByPointsConfigDto dto) {
        ZoneByPointsConfig dao = ZoneByPointsConfig.builder()
                .configName(dto.getConfigName())
                .pointsMultiplication(dto.isPointsMultiplication())
                .pointNeighborhoodVerification(dto.isPointNeighborhoodVerification())
                .minPointsNumberInSeries(dto.getMinPointsNumberInSeries())
                .maxPausesNumberBetweenPoints(dto.getMaxPausesNumberBetweenPoints())
                .build();
        if (dto.isPointNeighborhoodVerification()) {
            dao.setRadiusOfPixelsToBeVerified(dto.getRadiusOfPixelsToBeVerified());
        }
        return dao;
    }
}
