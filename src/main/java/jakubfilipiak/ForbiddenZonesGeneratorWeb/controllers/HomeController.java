package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.MapConfigService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByPointsConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    public HomeController(MapConfigService mapConfigService, LocalFileService localFileService, ZoneByPointsConfigService zoneByPointsConfigService) {
        this.mapConfigService = mapConfigService;
        this.localFileService = localFileService;
        this.zoneByPointsConfigService = zoneByPointsConfigService;
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
}
