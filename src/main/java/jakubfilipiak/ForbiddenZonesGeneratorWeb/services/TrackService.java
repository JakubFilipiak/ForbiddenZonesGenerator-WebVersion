package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.mappers.TrackMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.TrackDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories.TrackRepository;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.config.MapConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.config.ProcessingConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.config.ZoneByPointsConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.config.ZoneByTurnsConfigService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private TrackRepository trackRepository;

//    private PointOfTrackService pointOfTrackService = new PointOfTrackService();
//    private TurnOfTrackGenerator turnOfTrackGenerator = new TurnOfTrackGenerator();
//    private ZoneByTurnsGenerator zoneByTurnsGenerator = new ZoneByTurnsGenerator();


    public TrackService(
            TrackMapper trackMapper,
            MapConfigService mapConfigService,
            ZoneByPointsConfigService zoneByPointsConfigService,
            ZoneByTurnsConfigService zoneByTurnsConfigService,
            ProcessingConfigService processingConfigService,
            LocalFileService localFileService,
            TrackRepository trackRepository) {
        this.trackMapper = trackMapper;
        this.mapConfigService = mapConfigService;
        this.zoneByPointsConfigService = zoneByPointsConfigService;
        this.zoneByTurnsConfigService = zoneByTurnsConfigService;
        this.processingConfigService = processingConfigService;
        this.localFileService = localFileService;
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
        Optional<Track> track = trackRepository.findByTrackName(trackName);
        if (track.isPresent()) {
            System.out.println("Processing track: " + track.toString());
        }


    }


//    public void processPointsOfTrack(BufferedReader bufferedReader) throws IOException {
//
//        String line;
//
//        while ((line = bufferedReader.readLine()) != null) {
//
//            if (line.startsWith("T")) {
//                PointOfTrack pointOfTrack = createPointOfTrack(line);
//
//                if (pointOfTrackService.isPointOfTrackCorrect(pointOfTrack)) {
//                    boolean bufferReady =
//                            turnOfTrackGenerator.updatePointsBuffer(pointOfTrack);
//
//                    if (bufferReady) {
//                        TurnOfTrack turnOfTrack = turnOfTrackGenerator.createTurnFromBuffer();
//                        System.out.println(turnOfTrack);
//                        boolean zoneBufferReady =
//                                zoneByTurnsGenerator.updateTurnsBuffer(turnOfTrack);
//                        if (zoneBufferReady) {
//                            ForbiddenZone zoneByTurns = zoneByTurnsGenerator.createZoneFromBuffer();
//                            System.out.println(zoneByTurns);
//                        }
//                    }
//                    System.out.println(pointOfTrack);
//                    System.out.println();
//                }
//            }
//        }
//    }
//
//    private PointOfTrack createPointOfTrack(String line) {
//
//        final int LATITUDE_INDEX = 2;
//        final int LONGITUDE_INDEX = 3;
//        final int TIME_INDEX = 5;
//
//        float latitude = 0f;
//        float longitude = 0f;
//        LocalTime time = LocalTime.of(0, 0, 0);
//
//        String[] fields = line.split(" ");
//        int fieldIndex = 0;
//
//        for (String field : fields) {
//            switch (fieldIndex) {
//                case LATITUDE_INDEX:
//                    latitude = Float.parseFloat(field.substring(1));
//                    break;
//                case LONGITUDE_INDEX:
//                    longitude = Float.parseFloat(field.substring(2));
//                    break;
//                case TIME_INDEX:
//                    time = LocalTime.parse(field);
//                    break;
//                default:
//                    break;
//            }
//            fieldIndex++;
//        }
//        return new PointOfTrack.PointOfTrackBuilder()
//                .setLatitude(latitude)
//                .setLongitude(longitude)
//                .setTime(time)
//                .build();
//    }
}
