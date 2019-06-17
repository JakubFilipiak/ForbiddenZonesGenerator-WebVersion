package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.MapConfigService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jakub Filipiak on 13.06.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class MapConfigController {

    private MapConfigService configService;
    private LocalFileService fileService;

    public MapConfigController(MapConfigService configService, LocalFileService fileService) {
        this.configService = configService;
        this.fileService = fileService;
    }

    @PostMapping(value = "/dto/map-configs", consumes = {"application/json",
            "multipart/form-data"})
    public void addConfig(@RequestParam("stringConfigDto") String stringConfigDto,
                          @RequestParam("file") MultipartFile file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MapConfigDto configDto = mapper.readValue(stringConfigDto, MapConfigDto.class);
        fileService.uploadFile(file).ifPresent(value ->
                configService.addConfig(configDto, value));
    }

    @GetMapping("/dto/map-configs")
    public List<MapConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/map-configs")
    public void updateConfig(@RequestBody MapConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/map-configs/{configName}")
    public void deleteConfig(@PathVariable String configName) {
        configService.getConfigByConfigName(configName).ifPresent(config -> {
            fileService.deleteFile(config.getFilePathname());
            configService.deleteConfig(configName);
        });
    }
}
