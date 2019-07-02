package jakubfilipiak.ForbiddenZonesGeneratorWeb.services;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TypeOfZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Jakub Filipiak on 30.06.2019.
 */
@Service
public class ForbiddenZonesService {

    public List<ForbiddenZone> sortAndMergeZones(
            Map<TypeOfZone, List<ForbiddenZone>> zonesMap) {
        List<ForbiddenZone> allZones = new ArrayList<>();
        allZones.addAll(zonesMap.get(TypeOfZone.BY_DROP_TIME));
        allZones.addAll(zonesMap.get(TypeOfZone.BY_POINTS));
        allZones.addAll(zonesMap.get(TypeOfZone.BY_TURNS));
        if (allZones.isEmpty() || allZones.size() == 1)
            return allZones;


        Collections.sort(allZones);

        List<ForbiddenZone> finalZones = new ArrayList<>();

        ForbiddenZone firstZone = allZones.get(0);
        LocalTime entranceTime = firstZone.getEntranceTime();
        LocalTime departureTime = firstZone.getDepartureTime();

        for (int i = 1; i < allZones.size(); i++) {
            ForbiddenZone currentZone = allZones.get(i);
            if (currentZone.getEntranceTime().isBefore(departureTime)
                    || currentZone.getEntranceTime().equals(departureTime)) {
                if (currentZone.getDepartureTime().isAfter(departureTime)) {
                    departureTime = currentZone.getDepartureTime();
                }
            } else {
                finalZones.add(ForbiddenZone.fromTimestamps(
                        entranceTime, departureTime));
                entranceTime = currentZone.getEntranceTime();
                departureTime = currentZone.getDepartureTime();
            }
        }

        finalZones.add(ForbiddenZone.fromTimestamps(
                entranceTime, departureTime));

        return finalZones;
    }
}
