package jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.commons.Mapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.TrackDto;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 18.06.2019.
 */
@Component
public class TrackMapper implements Mapper<Track, TrackDto> {

    @Override
    public TrackDto map(Track dao) {
        TrackDto dto =  TrackDto
                .builder()
                .trackName(dao.getTrackName())
                .processingConfigName(dao.getProcessingConfig().getConfigName())
                .originalTrackFileName(dao.getTrackFile().getOriginalName())
                .uniqueTrackFileName(dao.getTrackFile().getUniqueName())
                .dropStartTime(dao.getDropStartTime().toString())
                .dropStopTime(dao.getDropStopTime().toString())
                .verified(dao.isVerified())
                .deleted(dao.isDeleted())
                .processed(dao.isProcessed())
                .build();

        if (dao.getMapConfig() != null) {
            dto.setMapConfigName(dao.getMapConfig().getConfigName());
        }
        if (dao.getZoneByPointsConfig() != null) {
            dto.setZoneByPointsConfigName(dao.getZoneByPointsConfig().getConfigName());
        }
        if (dao.getZoneByTurnsConfig() != null) {
            dto.setZoneByTurnsConfigName(dao.getZoneByTurnsConfig().getConfigName());
        }
        if (dao.getOutputFile() != null) {
            dto.setOriginalOutputFileName(dao.getOutputFile().getOriginalName());
            dto.setUniqueOutputFileName(dao.getOutputFile().getUniqueName());
        }
        return dto;
    }

    @Override
    public Track reverseMap(TrackDto dto) {
        return Track
                .builder()
                .trackName(dto.getTrackName())
                .dropStartTime(LocalTime.parse(dto.getDropStartTime()))
                .dropStopTime(LocalTime.parse(dto.getDropStopTime()))
                .build();
    }
}
