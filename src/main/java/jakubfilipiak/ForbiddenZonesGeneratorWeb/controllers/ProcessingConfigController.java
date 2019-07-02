package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ProcessingConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ProcessingConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProcessingConfigController {

    private ProcessingConfigService configService;

    public ProcessingConfigController(ProcessingConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/dto/processing-configs")
    public void addConfig(@RequestBody ProcessingConfigDto configDto) {
        configService.addConfig(configDto);
    }

    @GetMapping("/dto/processing-configs")
    public List<ProcessingConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/processing-configs")
    public void updateConfig(@RequestBody ProcessingConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/processing-configs")
    public void deleteConfig(@RequestParam String configName) {
        configService.setConfigAsDeleted(configName);
    }
}
