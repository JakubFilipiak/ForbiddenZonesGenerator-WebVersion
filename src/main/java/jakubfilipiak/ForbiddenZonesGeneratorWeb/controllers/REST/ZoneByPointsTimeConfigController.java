package jakubfilipiak.ForbiddenZonesGeneratorWeb.controllers.REST;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.dtos.ZoneByPointsTimeConfigDto;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.services.configServices.ZoneByPointsTimeConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Jakub Filipiak on 15.07.2019.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class ZoneByPointsTimeConfigController {

    private ZoneByPointsTimeConfigService configService;

    public ZoneByPointsTimeConfigController(ZoneByPointsTimeConfigService configService) {
        this.configService = configService;
    }

    @PostMapping("/dto/zone-by-points-time-configs")
    public void addConfig(@RequestBody ZoneByPointsTimeConfigDto configDto) {
        configService.addConfig(configDto);
    }

    @GetMapping("/dto/zone-by-points-time-configs/verify")
    public void verifyConfig(@RequestParam String configName) {
        configService.verifyConfig(configName);
    }

    @GetMapping("/dto/zone-by-points-time-configs")
    public List<ZoneByPointsTimeConfigDto> getConfigsDto() {
        return configService.getConfigsDto();
    }

    @PutMapping("/dto/zone-by-points-time-configs")
    public void updateConfig(@RequestBody ZoneByPointsTimeConfigDto configDto) {
        configService.updateConfig(configDto);
    }

    @DeleteMapping("/dto/zone-by-points-time-configs")
    public void deleteConfig(@RequestParam String configName) {
        configService.setConfigAsDeleted(configName);
    }
}
