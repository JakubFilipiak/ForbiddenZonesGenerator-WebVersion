package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TypeOfZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.TrackMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.TrackDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.TrackRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.MapConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ProcessingConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByPointsConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByTurnsConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.fileServices.TxtService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators.AllTypesOfZonesGenerator;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.io.TrkReader;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.validators.TrackValidator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
@Service
public class TrackService {

    private TrackMapper trackMapper;
    private MapConfigService mapConfigService;
    private ZoneByPointsConfigService zoneByPointsConfigService;
    private ZoneByTurnsConfigService zoneByTurnsConfigService;
    private ProcessingConfigService processingConfigService;
    private LocalFileService localFileService;
    private PointOfTrackService pointOfTrackService;
    private ForbiddenZonesService forbiddenZonesService;
    private TxtService txtService;
    private TrackRepository trackRepository;

    public TrackService(
            TrackMapper trackMapper,
            MapConfigService mapConfigService,
            ZoneByPointsConfigService zoneByPointsConfigService,
            ZoneByTurnsConfigService zoneByTurnsConfigService,
            ProcessingConfigService processingConfigService,
            LocalFileService localFileService,
            PointOfTrackService pointOfTrackService,
            ForbiddenZonesService forbiddenZonesService,
            TxtService txtService,
            TrackRepository trackRepository) {
        this.trackMapper = trackMapper;
        this.mapConfigService = mapConfigService;
        this.zoneByPointsConfigService = zoneByPointsConfigService;
        this.zoneByTurnsConfigService = zoneByTurnsConfigService;
        this.processingConfigService = processingConfigService;
        this.localFileService = localFileService;
        this.pointOfTrackService = pointOfTrackService;
        this.forbiddenZonesService = forbiddenZonesService;
        this.txtService = txtService;
        this.trackRepository = trackRepository;
    }

    public void addTrack(TrackDto trackDto, LocalFile trackFile) {
        Track track = trackMapper.reverseMap(trackDto);
        track.setTrackFile(trackFile);
        mapConfigService
                .getConfigByConfigName(trackDto.getMapConfigName())
                .ifPresent(track::setMapConfig);
        zoneByPointsConfigService
                .getConfigByConfigName(trackDto.getZoneByPointsConfigName())
                .ifPresent(track::setZoneByPointsConfig);
        zoneByTurnsConfigService
                .getConfigByConfigName(trackDto.getZoneByTurnsConfigName())
                .ifPresent(track::setZoneByTurnsConfig);
        processingConfigService
                .getConfigByConfigName(trackDto.getProcessingConfigName())
                .ifPresent(track::setProcessingConfig);
        trackRepository.save(track);
    }

    public void verifyTrack(String trackName) {
        trackRepository
                .findByTrackName(trackName)
                .ifPresent(track -> {
                    TrackValidator validator = new TrackValidator(track);
                    if (validator.isEachParamPresent())
                        if (validator.isEachParamCorrect()) {
                            track.setVerified(true);
                            trackRepository.save(track);
                        }
                });
    }

    public List<TrackDto> getTracksDto() {
        return trackRepository
                .findAllNotDeleted()
                .stream()
                .map(trackMapper::map)
                .collect(Collectors.toList());
    }

    public void setTrackAsDeleted(String trackName) {
        trackRepository
                .findByTrackName(trackName)
                .ifPresent(track -> {
                    track.setDeleted(true);
                    track.setTrackName(createDeprecatedName(trackName));
                    trackRepository.save(track);
                });
    }

    private String createDeprecatedName(String configName) {
        final String DATE_FORMAT = "yyyy-MM-dd---HH-mm-ss-";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String localTimeNow = LocalDateTime.now().format(formatter);
        String prefix = "DEPRECATED-from-";
        return prefix + localTimeNow + configName;
    }


    public void processTrack(String trackName) {
        trackRepository
                .findByTrackName(trackName)
                .ifPresent(track -> {
                    createForbiddenZones(track);
                    if (isZonesCreationCompletedSuccessfully(track))
                        writeZonesIntoNewFileAndUpdateTrack(track);
                });
    }

    private void createForbiddenZones(Track track) {
        AllTypesOfZonesGenerator zonesGenerator =
                new AllTypesOfZonesGenerator(track);
        TrkReader trkReader =
                new TrkReader(track.getTrackFile().getPathName());

        if (trkReader.isReady()) {
            Optional<String> optRawDataLine;
            String rawDataLine;
            while (true) {
                optRawDataLine = trkReader.readLine();
                if (optRawDataLine.isPresent()) {
                    rawDataLine = optRawDataLine.get();
                    if (trkReader.isLineCorrect(rawDataLine)) {
                        pointOfTrackService
                                .createPointFromLine(rawDataLine)
                                .ifPresent(zonesGenerator::updateBuffer);
                    }
                } else {
                    trkReader.close();
                    break;
                }
            }
        }
        zonesGenerator.closeOpenedZones();
        assignZonesToTrack(zonesGenerator.getMapOfZonesCreatedFromBuffer(), track);
    }

    private boolean isZonesCreationCompletedSuccessfully(Track track) {
        Map<TypeOfZone, List<ForbiddenZone>> assignedZones = track.getZonesMap();
        if (assignedZones != null) {
            if (assignedZones.containsKey(TypeOfZone.ALL_MERGED)) {
                return !assignedZones.get(TypeOfZone.ALL_MERGED).isEmpty();
            }
        }
        return false;
    }

    private void writeZonesIntoNewFileAndUpdateTrack(Track track) {
        localFileService
                .createFileInStorageDir(txtService.createFileNameFromTrack(track))
                .ifPresent(txtLocalFile -> {
                    File txtFile = new File(txtLocalFile.getPathName());
                    txtService.writeOnlyMergedZones(track, txtFile);
                    track.setOutputFile(txtLocalFile);
                    track.setProcessed(true);
                    trackRepository.save(track);
                });
        localFileService
                .createFileInStorageDir(txtService.createDebugFileNameFromTrack(track))
                .ifPresent(txtLocalFile -> {
                    File txtFileInDebugMode = new File(txtLocalFile.getPathName());
                    txtService.writeAllTypesOfZones(track, txtFileInDebugMode);
                    track.setOutputFileInDebugMode(txtLocalFile);
                    trackRepository.save(track);
                });
    }

    private void assignZonesToTrack(
            Map<TypeOfZone, List<ForbiddenZone>> zonesToBeAssigned, Track track) {
        zonesToBeAssigned.put(
                TypeOfZone.ALL_MERGED,
                forbiddenZonesService.sortAndMergeZones(zonesToBeAssigned));
        track.setZonesMap(zonesToBeAssigned);
    }
}
