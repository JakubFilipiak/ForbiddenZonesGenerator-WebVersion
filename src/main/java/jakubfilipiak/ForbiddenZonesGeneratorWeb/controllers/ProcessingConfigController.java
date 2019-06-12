package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ProcessingConfig;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ProcessingConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.ProcessingConfigService;
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
    public ProcessingConfig addConfig(@RequestBody ProcessingConfigDto configDto) {
        return configService.addConfig(configDto);
    }

    @GetMapping("/dto/processing-configs")
    public List<ProcessingConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/processing-configs")
    public void updateConfig(@RequestBody ProcessingConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/processing-configs/{configName}")
    public void deleteConfig(@PathVariable String configName) {
        configService.deleteConfig(configName);
    }
}
