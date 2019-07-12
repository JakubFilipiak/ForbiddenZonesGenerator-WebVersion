package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ProcessingConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.MapConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ProcessingConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByPointsConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByTurnsConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    private ProcessingConfigService processingConfigService;

    public HomeController(MapConfigService mapConfigService, LocalFileService localFileService, ZoneByPointsConfigService zoneByPointsConfigService, ZoneByTurnsConfigService zoneByTurnsConfigService, ProcessingConfigService processingConfigService) {
        this.mapConfigService = mapConfigService;
        this.localFileService = localFileService;
        this.zoneByPointsConfigService = zoneByPointsConfigService;
        this.zoneByTurnsConfigService = zoneByTurnsConfigService;
        this.processingConfigService = processingConfigService;
    }

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
}
