package jakubfilipiak.ForbiddenZonesGeneratorWeb.services.fileServices;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TypeOfZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.io.TxtWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Jakub Filipiak on 24.06.2019.
 */
@Service
public class TxtService {

    public static final String TXT_FILE_EXTENSION = ".txt";

    public String createFileNameFromTrack(Track track) {
        return createBasisOfName(track) + TXT_FILE_EXTENSION;
    }

    public String createDebugFileNameFromTrack(Track track) {
        return createBasisOfName(track) + "-DEBUG" + TXT_FILE_EXTENSION;
    }

    public void writeAllTypesOfZones(Track track, File txtFileInDebugMode) {
        TxtWriter txtWriter = new TxtWriter(txtFileInDebugMode);
        if (txtWriter.isReady()) {
            track.getZonesMap().forEach((typeOfZone, forbiddenZones) -> {
                txtWriter.writeLine(typeOfZone.toString());
                forbiddenZones.forEach(zone -> txtWriter.writeLine(zone.toString()));
            });
            txtWriter.close();
        }
    }

    public void writeOnlyMergedZones(Track track, File txtFile) {
        TxtWriter txtWriter = new TxtWriter(txtFile);
        if (txtWriter.isReady()) {
            track.getZonesMap()
                    .get(TypeOfZone.ALL_MERGED)
                    .forEach(zone -> txtWriter.writeLine(zone.toString()));
            txtWriter.close();
        }
    }

    private String createBasisOfName(Track track) {
        String trackName = track.getTrackName();
        boolean isZoneByDropTime =
                track.getProcessingConfig().isZoneByDropTimeCreation();
        boolean isZoneByPoints =
                track.getProcessingConfig().isZoneByPointsCreation();
        boolean isZoneByTurns =
                track.getProcessingConfig().isZoneByTurnsCreation();
        return trackName
                + "-time-" + isZoneByDropTime
                + "-points-" + isZoneByPoints
                + "-turns-" + isZoneByTurns;
    }
}
