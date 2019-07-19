package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ProcessingConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ProcessingConfigDto;
import org.springframework.stereotype.Component;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Component
public class ProcessingConfigMapper implements Mapper<ProcessingConfig, ProcessingConfigDto> {

    @Override
    public ProcessingConfigDto map(ProcessingConfig dao) {
        return ProcessingConfigDto.builder()
                .configName(dao.getConfigName())
                .zoneByDropTimeCreation(dao.isZoneByDropTimeCreation())
                .zoneByPointsCreation(dao.isZoneByPointsCreation())
                .zoneByTurnsCreation(dao.isZoneByTurnsCreation())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .build();
    }

    @Override
    public ProcessingConfig reverseMap(ProcessingConfigDto dto) {
        return ProcessingConfig.builder()
                .configName(dto.getConfigName())
                .zoneByDropTimeCreation(dto.isZoneByDropTimeCreation())
                .zoneByPointsCreation(dto.isZoneByPointsCreation())
                .zoneByTurnsCreation(dto.isZoneByTurnsCreation())
                .build();
    }
}
