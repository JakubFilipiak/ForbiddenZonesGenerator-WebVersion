package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.TrackDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.TrackService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jakub Filipiak on 18.06.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class TrackController {

    private LocalFileService fileService;
    private TrackService trackService;
    private ObjectMapper jsonMapper;

    public TrackController(LocalFileService fileService,
                           TrackService trackService) {
        this.fileService = fileService;
        this.trackService = trackService;
        jsonMapper = new ObjectMapper();
    }

    @PostMapping(value = "/dto/tracks", consumes = "multipart/form-data")
    public void addTrack(
            @RequestParam("stringTrackDto") String jsonTrackDto,
            @RequestParam("file") MultipartFile file) throws IOException {
        TrackDto trackDto = jsonMapper.readValue(jsonTrackDto, TrackDto.class);
        fileService.uploadFile(file).ifPresent(localFile ->
                        trackService.addTrack(trackDto, localFile));
    }

    @GetMapping("/dto/tracks/verify")
    public void verifyConfig(@RequestParam String trackName) {
        trackService.verifyTrack(trackName);
    }

    @PostMapping(value = "/dto/tracks/from-existing")
    public void addTrackFromExistingTrack(
            @RequestParam("existingUniqueFileName") String existingUniqueFileName,
            @RequestParam("stringTrackDto") String jsonTrackDto) throws IOException {
        TrackDto trackDto = jsonMapper.readValue(jsonTrackDto, TrackDto.class);
        fileService.getLocalFileByUniqueName(existingUniqueFileName).ifPresent(localFile ->
                trackService.addTrack(trackDto, localFile));
    }

    @GetMapping(value = "/dto/tracks")
    public List<TrackDto> getTracksDto() {
        return trackService.getTracksDto();
    }

    @DeleteMapping(value = "/dto/tracks")
    public void deleteTrack(@RequestParam String trackName) {
        trackService.setTrackAsDeleted(trackName);
    }

    @GetMapping(value = "/dto/tracks/process")
    public void processTrack(@RequestParam String trackName) {
        trackService.processTrack(trackName);
    }
}
