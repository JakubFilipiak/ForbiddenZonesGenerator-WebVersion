package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers.REST;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByTurnsTimeConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByTurnsTimeConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jakub Filipiak on 15.07.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ZoneByTurnsTimeConfigController {

    private ZoneByTurnsTimeConfigService configService;

    public ZoneByTurnsTimeConfigController(ZoneByTurnsTimeConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/dto/zone-by-turns-time-configs")
    public void addConfig(@RequestBody ZoneByTurnsTimeConfigDto configDto) {
        configService.addConfig(configDto);
    }

    @GetMapping("/dto/zone-by-turns-time-configs/verify")
    public void verifyConfig(@RequestParam String configName) {
        configService.verifyConfig(configName);
    }

    @GetMapping("/dto/zone-by-turns-time-configs")
    public List<ZoneByTurnsTimeConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/zone-by-turns-time-configs")
    public void updateConfig(@RequestBody ZoneByTurnsTimeConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/zone-by-turns-time-configs")
    public void deleteConfig(@RequestParam String configName) {
        configService.setConfigAsDeleted(configName);
    }
}
