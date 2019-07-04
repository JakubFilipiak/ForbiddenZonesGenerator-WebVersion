package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ProcessingConfig;

import java.io.File;
import java.time.LocalTime;

/**
 * Created by Jakub Filipiak on 03.07.2019.
 */
public class TrackValidator {

    private Track track;

    public TrackValidator(Track track) {
        this.track = track;
    }

    public boolean isEachParamPresent() {
        boolean isTrackFilePresent =
                track.getTrackFile() != null &&
                        new File(track.getTrackFile().getPathName()).exists();
        boolean isMapConfigPresent = track.getMapConfig() != null;
        boolean isProcessingConfigPresent = track.getProcessingConfig() != null;
        return isTrackFilePresent &&
                isMapConfigPresent &&
                isProcessingConfigPresent &&
                isEachRequiredZoneConfigPresent();
    }

    public boolean isEachParamCorrect() {
        boolean isMapConfigVerified = track.getMapConfig().isVerified();
        boolean isProcessingConfigVerified = track.getProcessingConfig().isVerified();
        return isMapConfigVerified &&
                isProcessingConfigVerified &&
                isEachRequiredZoneConfigVerified();
    }

    private boolean isEachRequiredZoneConfigPresent() {
        ProcessingConfig processingConfig = track.getProcessingConfig();
        if (processingConfig.isZoneByDropTimeCreation()) {
            if (!isEachTimestampPresent())
                return false;
        }
        if (processingConfig.isZoneByTurnsCreation()) {
            if (track.getZoneByTurnsConfig() == null)
                return false;
        }
        if (processingConfig.isZoneByTurnsCreation()) {
            if (track.getZoneByTurnsConfig() == null)
                return false;
        }
        return true;
    }

    private boolean isEachTimestampPresent() {
        boolean dropStartTimePresent = isTimestampPresent(track.getDropStartTime());
        boolean dropStopTimePresent = isTimestampPresent(track.getDropStopTime());
        return dropStartTimePresent && dropStopTimePresent;
    }

    private boolean isTimestampPresent(LocalTime timestamp) {
        return timestamp != null;
    }

    private boolean isEachRequiredZoneConfigVerified() {
        ProcessingConfig processingConfig = track.getProcessingConfig();
        if (processingConfig.isZoneByDropTimeCreation()) {
            if (!isEachTimestampCorrect())
                return false;
        }
        if (processingConfig.isZoneByPointsCreation()) {
            if (!track.getZoneByPointsConfig().isVerified())
                return false;
        }
        if (processingConfig.isZoneByTurnsCreation()) {
            if (!track.getZoneByTurnsConfig().isVerified())
                return false;
        }
        return true;
    }

    private boolean isEachTimestampCorrect() {
        return track.getDropStartTime().isBefore(track.getDropStopTime());
    }
}
