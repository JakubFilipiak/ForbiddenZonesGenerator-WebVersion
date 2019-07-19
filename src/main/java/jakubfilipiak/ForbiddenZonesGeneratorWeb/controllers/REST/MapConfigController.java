package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers.REST;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.MapConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.LocalFileService;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.MapConfigService;
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
    private ObjectMapper jsonMapper;

    public MapConfigController(MapConfigService configService, LocalFileService fileService) {
        this.configService = configService;
        this.fileService = fileService;
        jsonMapper = new ObjectMapper();
    }

    @PostMapping(value = "/dto/map-configs", consumes = "multipart/form-data")
    public void addConfig(
            @RequestParam("stringConfigDto") String jsonConfigDto,
            @RequestParam("file") MultipartFile file) throws IOException {
        MapConfigDto configDto =
                jsonMapper.readValue(jsonConfigDto, MapConfigDto.class);
        fileService
                .uploadFile(file)
                .ifPresent(uploadedFile ->
                        configService.addConfig(configDto, uploadedFile));
    }

    @PostMapping(value = "/dto/map-configs/from-existing")
    public void addConfigFromExistingConfig(
            @RequestParam("existingUniqueFileName") String existingUniqueFileName,
            @RequestParam("stringConfigDto") String jsonConfigDto) throws IOException {
        MapConfigDto configDto =
                jsonMapper.readValue(jsonConfigDto, MapConfigDto.class);
        fileService
                .getLocalFileByUniqueName(existingUniqueFileName)
                .ifPresent(localFile -> configService.addConfig(configDto, localFile));
    }

    @GetMapping("/dto/map-configs/verify")
    public void verifyConfig(@RequestParam String configName) {
        configService.verifyConfig(configName);
    }

    @GetMapping("/dto/map-configs")
    public List<MapConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/map-configs")
    public void updateConfig(@RequestBody MapConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/map-configs")
    public void deleteConfig(@RequestParam String configName) {
        configService.setConfigAsDeleted(configName);
    }
}
