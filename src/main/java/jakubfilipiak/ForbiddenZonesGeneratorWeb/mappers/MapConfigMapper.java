package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.ColorService;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Component
public class MapConfigMapper implements Mapper<MapConfig, MapConfigDto> {

    private ColorService colorService;

    public MapConfigMapper(ColorService colorService) {
        this.colorService = colorService;
    }

    @Override
    public MapConfigDto map(MapConfig dao) {
        return MapConfigDto
                .builder()
                .configName(dao.getConfigName())
                .filename(dao.getFilename())
                .allowedRGBColor(dao.getAllowedRGBColor())
                .forbiddenRGBColor(dao.getForbiddenRGBColor())
                .bottomLeftCornerLatitude(dao.getBottomLeftCornerLatitude())
                .bottomLeftCornerLongitude(dao.getBottomLeftCornerLongitude())
                .upperRightCornerLatitude(dao.getUpperRightCornerLatitude())
                .upperRightCornerLongitude(dao.getUpperRightCornerLongitude())
                .verified(dao.isVerified())
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
