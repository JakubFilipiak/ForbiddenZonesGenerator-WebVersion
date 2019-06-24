package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Component
public class MapConfigMapper implements Mapper<MapConfig, MapConfigDto> {

    @Override
    public MapConfigDto map(MapConfig dao) {
        return MapConfigDto
                .builder()
                .configName(dao.getConfigName())
                .originalFileName(dao.getMapFile().getOriginalName())
                .uniqueFileName(dao.getMapFile().getUniqueName())
                .allowedRGBColor(dao.getAllowedRGBColor())
                .forbiddenRGBColor(dao.getForbiddenRGBColor())
                .bottomLeftCornerLatitude(dao.getBottomLeftCornerLatitude())
                .bottomLeftCornerLongitude(dao.getBottomLeftCornerLongitude())
                .upperRightCornerLatitude(dao.getUpperRightCornerLatitude())
                .upperRightCornerLongitude(dao.getUpperRightCornerLongitude())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();
    }

    @Override
    public MapConfig reverseMap(MapConfigDto dto) {
        return MapConfig
                .builder()
                .configName(dto.getConfigName())
                .allowedRGBColor(dto.getAllowedRGBColor())
                .forbiddenRGBColor(dto.getForbiddenRGBColor())
                .bottomLeftCornerLatitude(dto.getBottomLeftCornerLatitude())
                .bottomLeftCornerLongitude(dto.getBottomLeftCornerLongitude())
                .upperRightCornerLatitude(dto.getUpperRightCornerLatitude())
                .upperRightCornerLongitude(dto.getUpperRightCornerLongitude())
                .build();
    }
}
