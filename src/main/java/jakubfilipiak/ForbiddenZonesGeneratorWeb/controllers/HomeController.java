package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.MapConfigService;
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

    public HomeController(MapConfigService mapConfigService, LocalFileService localFileService) {
        this.mapConfigService = mapConfigService;
        this.localFileService = localFileService;
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
}
