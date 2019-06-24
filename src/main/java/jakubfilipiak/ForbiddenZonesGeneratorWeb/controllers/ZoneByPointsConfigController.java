package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.config.ZoneByPointsConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jakub Filipiak on 21.06.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ZoneByPointsConfigController {

    private ZoneByPointsConfigService configService;

    public ZoneByPointsConfigController(ZoneByPointsConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/dto/zone-by-points-configs")
    public void addConfig(@RequestBody ZoneByPointsConfigDto configDto) {
        configService.addConfig(configDto);
    }

    @GetMapping("/dto/zone-by-points-configs")
    public List<ZoneByPointsConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/zone-by-points-configs")
    public void updateConfig(@RequestBody ZoneByPointsConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/zone-by-points-configs")
    public void deleteConfig(@RequestParam String configName) {
        configService.setConfigAsDeleted(configName);
    }
}
