package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByTurnsConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jakub Filipiak on 21.06.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ZoneByTurnsConfigController {

    private ZoneByTurnsConfigService configService;

    public ZoneByTurnsConfigController(ZoneByTurnsConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/dto/zone-by-turns-configs")
    public void addConfig(@RequestBody ZoneByTurnsConfigDto configDto) {
        configService.addConfig(configDto);
    }

    @GetMapping("/dto/zone-by-turns-configs")
    public List<ZoneByTurnsConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/zone-by-turns-configs")
    public void updateConfig(@RequestBody ZoneByTurnsConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/zone-by-turns-configs")
    public void deleteConfig(@RequestParam String configName) {
        configService.setConfigAsDeleted(configName);
    }
}
