package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.*;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.dtos.TrackDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.TrackService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 09.07.2019.
 */
@Controller
public class HomeController {

    private MapConfigService mapConfigService;
    private LocalFileService localFileService;
    private ZoneByPointsConfigService zoneByPointsConfigService;
    private ZoneByTurnsConfigService zoneByTurnsConfigService;
    private ZoneByPointsTimeConfigService zoneByPointsTimeConfigService;
    private ZoneByTurnsTimeConfigService zoneByTurnsTimeConfigService;
    private ProcessingConfigService processingConfigService;
    private TrackService trackService;

    public HomeController(MapConfigService mapConfigService, LocalFileService localFileService, ZoneByPointsConfigService zoneByPointsConfigService, ZoneByTurnsConfigService zoneByTurnsConfigService, ZoneByPointsTimeConfigService zoneByPointsTimeConfigService, ZoneByTurnsTimeConfigService zoneByTurnsTimeConfigService, ProcessingConfigService processingConfigService, TrackService trackService) {
        this.mapConfigService = mapConfigService;
        this.localFileService = localFileService;
        this.zoneByPointsConfigService = zoneByPointsConfigService;
        this.zoneByTurnsConfigService = zoneByTurnsConfigService;
        this.zoneByPointsTimeConfigService = zoneByPointsTimeConfigService;
        this.zoneByTurnsTimeConfigService = zoneByTurnsTimeConfigService;
        this.processingConfigService = processingConfigService;
        this.trackService = trackService;
    }

    // ************************************************************************
    // MapConfigs
    // ************************************************************************

    @GetMapping("/mapconfigs")
    public String getMapConfigs(Model model) {
        model.addAttribute("mapConfigs", mapConfigService.getConfigsDto());
        return "mapconfigs";
    }

    @PostMapping(value = "/mapconfigs", consumes = "multipart/form-data")
    public String addMapConfig(@RequestParam("file") MultipartFile file,
                               @ModelAttribute MapConfigDto configDto,
                               Model model) {
        List<String> existingConfigNames = mapConfigService.getConfigsDto()
                        .stream()
                        .map(MapConfigDto::getConfigName)
                        .collect(Collectors.toList());
        if (existingConfigNames.contains(configDto.getConfigName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("mapConfigs", mapConfigService.getConfigsDto());
            model.addAttribute("wrongConfig", configDto);
            model.addAttribute("mapFile", file);
            return "mapconfigs";
        } else {
            localFileService
                    .uploadFile(file)
                    .ifPresent(uploadedFile ->
                            mapConfigService.addConfig(configDto, uploadedFile));
            return "redirect:/mapconfigs";
        }
    }

    @GetMapping("/mapconfigs/verify")
    public String verifyMapConfig(@RequestParam("configName") String configName) {
        mapConfigService.verifyConfig(configName);
        return "redirect:/mapconfigs";
    }

    @GetMapping("/mapconfigs/delete")
    public String deleteMapConfig(@RequestParam("configName") String configName) {
        mapConfigService.setConfigAsDeleted(configName);
        return "redirect:/mapconfigs";
    }

    // ************************************************************************
    // ZoneByPointsConfigs
    // ************************************************************************

    @GetMapping("/zonebypointsconfigs")
    public String getZoneByPointsConfigs(Model model) {
        model.addAttribute("zoneByPointsConfigs", zoneByPointsConfigService.getConfigsDto());
        return "zonebypointsconfigs";
    }

    @PostMapping(value = "/zonebypointsconfigs", consumes = "multipart/form-data")
    public String addZoneByPointsConfig(@ModelAttribute ZoneByPointsConfigDto configDto,
                                        Model model) {
        List<String> existingConfigNames = zoneByPointsConfigService.getConfigsDto()
                .stream()
                .map(ZoneByPointsConfigDto::getConfigName)
                .collect(Collectors.toList());
        if (existingConfigNames.contains(configDto.getConfigName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("zoneByPointsConfigs", zoneByPointsConfigService.getConfigsDto());
            model.addAttribute("wrongConfig", configDto);
            return "zonebypointsconfigs";
        } else {
            zoneByPointsConfigService.addConfig(configDto);
            return "redirect:/zonebypointsconfigs";
        }
    }

    @GetMapping("/zonebypointsconfigs/verify")
    public String verifyZoneByPointsConfig(@RequestParam("configName") String configName) {
        zoneByPointsConfigService.verifyConfig(configName);
        return "redirect:/zonebypointsconfigs";
    }

    @GetMapping("/zonebypointsconfigs/delete")
    public String deleteZoneByPointsConfig(@RequestParam("configName") String configName) {
        zoneByPointsConfigService.setConfigAsDeleted(configName);
        return "redirect:/zonebypointsconfigs";
    }

    // ************************************************************************
    // ZoneByTurnsConfigs
    // ************************************************************************

    @GetMapping("/zonebyturnsconfigs")
    public String getZoneByTurnsConfigs(Model model) {
        model.addAttribute("zoneByTurnsConfigs", zoneByTurnsConfigService.getConfigsDto());
        return "zonebyturnsconfigs";
    }

    @PostMapping(value = "/zonebyturnsconfigs", consumes = "multipart/form-data")
    public String addZoneByTurnsConfig(@ModelAttribute ZoneByTurnsConfigDto configDto,
                                        Model model) {
        List<String> existingConfigNames = zoneByTurnsConfigService.getConfigsDto()
                .stream()
                .map(ZoneByTurnsConfigDto::getConfigName)
                .collect(Collectors.toList());
        if (existingConfigNames.contains(configDto.getConfigName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("zoneByTurnsConfigs",
                    zoneByTurnsConfigService.getConfigsDto());
            model.addAttribute("wrongConfig", configDto);
            return "zonebyturnsconfigs";
        } else {
            zoneByTurnsConfigService.addConfig(configDto);
            return "redirect:/zonebyturnsconfigs";
        }
    }

    @GetMapping("/zonebyturnsconfigs/verify")
    public String verifyZoneBTurnsConfig(@RequestParam("configName") String configName) {
        zoneByTurnsConfigService.verifyConfig(configName);
        return "redirect:/zonebyturnsconfigs";
    }

    @GetMapping("/zonebyturnsconfigs/delete")
    public String deleteZoneByTurnsConfig(@RequestParam("configName") String configName) {
        zoneByTurnsConfigService.setConfigAsDeleted(configName);
        return "redirect:/zonebyturnsconfigs";
    }

    // ************************************************************************
    // ZoneByPointsTimeConfigs
    // ************************************************************************

    @GetMapping("/zonebypointstimeconfigs")
    public String getZoneByPointsTimeConfigs(Model model) {
        model.addAttribute("zoneByPointsTimeConfigs",
                zoneByPointsTimeConfigService.getConfigsDto());
        return "zonebypointstimeconfigs";
    }

    @PostMapping(value = "/zonebypointstimeconfigs", consumes = "multipart/form-data")
    public String addZoneByPointsTimeConfig(@ModelAttribute ZoneByPointsTimeConfigDto configDto,
                                        Model model) {
        List<String> existingConfigNames = zoneByPointsTimeConfigService.getConfigsDto()
                .stream()
                .map(ZoneByPointsTimeConfigDto::getConfigName)
                .collect(Collectors.toList());
        if (existingConfigNames.contains(configDto.getConfigName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("zoneByPointsTimeConfigs",
                    zoneByPointsTimeConfigService.getConfigsDto());
            model.addAttribute("wrongConfig", configDto);
            return "zonebypointstimeconfigs";
        } else {
            zoneByPointsTimeConfigService.addConfig(configDto);
            return "redirect:/zonebypointstimeconfigs";
        }
    }

    @GetMapping("/zonebypointstimeconfigs/verify")
    public String verifyZoneByPointsTimeConfig(@RequestParam("configName") String configName) {
        zoneByPointsTimeConfigService.verifyConfig(configName);
        return "redirect:/zonebypointstimeconfigs";
    }

    @GetMapping("/zonebypointstimeconfigs/delete")
    public String deleteZoneByPointsTimeConfig(@RequestParam("configName") String configName) {
        zoneByPointsTimeConfigService.setConfigAsDeleted(configName);
        return "redirect:/zonebypointstimeconfigs";
    }

    // ************************************************************************
    // ZoneByTurnsTimeConfigs
    // ************************************************************************

    @GetMapping("/zonebyturnstimeconfigs")
    public String getZoneByTurnsTimeConfigs(Model model) {
        model.addAttribute("zoneByTurnsTimeConfigs",
                zoneByTurnsTimeConfigService.getConfigsDto());
        return "zonebyturnstimeconfigs";
    }

    @PostMapping(value = "/zonebyturnstimeconfigs", consumes = "multipart/form-data")
    public String addZoneByTurnsTimeConfig(@ModelAttribute ZoneByTurnsTimeConfigDto configDto,
                                       Model model) {
        List<String> existingConfigNames =
                zoneByTurnsTimeConfigService.getConfigsDto()
                .stream()
                .map(ZoneByTurnsTimeConfigDto::getConfigName)
                .collect(Collectors.toList());
        if (existingConfigNames.contains(configDto.getConfigName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("zoneByTurnsTimeConfigs",
                    zoneByTurnsTimeConfigService.getConfigsDto());
            model.addAttribute("wrongConfig", configDto);
            return "zonebyturnstimeconfigs";
        } else {
            zoneByTurnsTimeConfigService.addConfig(configDto);
            return "redirect:/zonebyturnstimeconfigs";
        }
    }

    @GetMapping("/zonebyturnstimeconfigs/verify")
    public String verifyZoneBTurnsTimeConfig(@RequestParam("configName") String configName) {
        zoneByTurnsTimeConfigService.verifyConfig(configName);
        return "redirect:/zonebyturnstimeconfigs";
    }

    @GetMapping("/zonebyturnstimeconfigs/delete")
    public String deleteZoneByTurnsTimeConfig(@RequestParam("configName") String configName) {
        zoneByTurnsTimeConfigService.setConfigAsDeleted(configName);
        return "redirect:/zonebyturnstimeconfigs";
    }

    // ************************************************************************
    // ProcessingConfigs
    // ************************************************************************

    @GetMapping("/processingconfigs")
    public String getProcessingConfigs(Model model) {
        model.addAttribute("processingConfigs", processingConfigService.getConfigsDto());
        return "processingconfigs";
    }

    @PostMapping(value = "/processingconfigs", consumes = "multipart/form-data")
    public String addProcessingConfig(@ModelAttribute ProcessingConfigDto configDto,
                                       Model model) {
        List<String> existingConfigNames = processingConfigService.getConfigsDto()
                .stream()
                .map(ProcessingConfigDto::getConfigName)
                .collect(Collectors.toList());
        if (existingConfigNames.contains(configDto.getConfigName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("processingConfigs",
                    processingConfigService.getConfigsDto());
            model.addAttribute("wrongConfig", configDto);
            return "processingconfigs";
        } else {
            processingConfigService.addConfig(configDto);
            return "redirect:/processingconfigs";
        }
    }

    @GetMapping("/processingconfigs/verify")
    public String verifyProcessingConfig(@RequestParam("configName") String configName) {
        processingConfigService.verifyConfig(configName);
        return "redirect:/processingconfigs";
    }

    @GetMapping("/processingconfigs/delete")
    public String deleteProcessingConfig(@RequestParam("configName") String configName) {
        processingConfigService.setConfigAsDeleted(configName);
        return "redirect:/processingconfigs";
    }

    // ************************************************************************
    // Tracks
    // ************************************************************************

    @GetMapping("/newtrack")
    public String getTrackForm(Model model) {
        model.addAttribute("processingConfigsNames",
                processingConfigService.getVerifiedConfigsNames());
        model.addAttribute("mapConfigsNames",
                mapConfigService.getVerifiedConfigsNames());
        model.addAttribute("zoneByPointsConfigsNames", zoneByPointsConfigService.getVerifiedConfigsNames());
        model.addAttribute("zoneByTurnsConfigsNames",
                zoneByTurnsConfigService.getVerifiedConfigsNames());
        model.addAttribute("zoneByPointsTimeConfigsNames",
                zoneByPointsTimeConfigService.getVerifiedConfigsNames());
        model.addAttribute("zoneByTurnsTimeConfigsNames",
                zoneByTurnsTimeConfigService.getVerifiedConfigsNames());
        return "newtrack";
    }

    @GetMapping("/tracks")
    public String getTracks(Model model) {
        model.addAttribute("tracks", trackService.getTracksDto());
        return "tracks";
    }

    @PostMapping(value = "/tracks", consumes = "multipart/form-data")
    public String addTrack(@RequestParam("file") MultipartFile file,
                               @ModelAttribute TrackDto trackDto,
                               Model model) {
        List<String> existingTrackNames = trackService.getTracksDto()
                .stream()
                .map(TrackDto::getTrackName)
                .collect(Collectors.toList());
        if (existingTrackNames.contains(trackDto.getTrackName())) {
            model.addAttribute("message", "Błąd! Nazwa jest już w użyciu!");
            model.addAttribute("tracks", trackService.getTracksDto());
            model.addAttribute("wrongTrack", trackDto);
            return "tracks";
        } else {
            localFileService
                    .uploadFile(file)
                    .ifPresent(uploadedFile ->
                            trackService.addTrack(trackDto, uploadedFile));
            return "redirect:/tracks";
        }
    }

    @GetMapping("/tracks/verify")
    public String verifyTrack(@RequestParam("trackName") String trackName) {
        trackService.verifyTrack(trackName);
        return "redirect:/tracks";
    }

    @GetMapping("/tracks/process")
    public String processTrack(@RequestParam("trackName") String trackName) {
        trackService.processTrack(trackName);
        return "redirect:/tracks";
    }

    @GetMapping("/tracks/delete")
    public String deleteTrack(@RequestParam("trackName") String trackName) {
        trackService.setTrackAsDeleted(trackName);
        return "redirect:/tracks";
    }

    // ************************************************************************
    // LocalFiles
    // ************************************************************************

    @GetMapping("/download/{uniqueFileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String uniqueFileName) throws IOException {
        File file = localFileService.downloadFile(uniqueFileName);
        Path path = Paths.get(localFileService.getStoragePath() + uniqueFileName);
        Resource resource = new UrlResource(path.toUri());
        String contentType = Files.probeContentType(file.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/openinbrowser/{uniqueFileName}")
    public ResponseEntity<?> openFileInBrowser(@PathVariable String uniqueFileName) throws IOException {
        File file = localFileService.downloadFile(uniqueFileName);
        Path path = Paths.get(localFileService.getStoragePath() + uniqueFileName);
        Resource resource = new UrlResource(path.toUri());
        String contentType = Files.probeContentType(file.toPath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(file.length())
                .body(resource);
    }
}
