package jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.generators;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TypeOfZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.PointOfTrack;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.utils.TurnAngleCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jakub Filipiak on 26.06.2019.
 */
public class AllTypesOfZonesGenerator {

    private Track track;

    private boolean isZoneByDropTimeCreation;
    private boolean isZoneByPointsCreation;
    private boolean isZoneByTurnsCreation;

    private boolean isPointsMultiplication;

    private MultipliedPointsGenerator multipliedPointsGenerator;
    private TurnOfTrackGenerator turnOfTrackGenerator;

    private ZoneByDropTimeGenerator zoneByDropTimeGenerator;
    private ZoneByPointsGenerator zoneByPointsGenerator;
    private ZoneByTurnsGenerator zoneByTurnsGenerator;

    private List<ForbiddenZone> zonesByDropTime = new ArrayList<>();
    private List<ForbiddenZone> zonesByPoints = new ArrayList<>();
    private List<ForbiddenZone> zonesByTurns = new ArrayList<>();
    private Map<TypeOfZone, List<ForbiddenZone>> zonesMap = new HashMap<>();

    public AllTypesOfZonesGenerator(Track track) {
        this.track = track;
        initZonesToCreateConfig();
        initPointsMultiplicationConfig();
        initGenerators();
        initZonesMap();
    }

    public void updateBuffer(PointOfTrack pointOfTrack) {
        if (isZoneByDropTimeCreation)
            createZonesByDropTime(pointOfTrack);
        if (isZoneByPointsCreation)
            createZonesByPoints(pointOfTrack);
        if (isZoneByTurnsCreation)
            createZonesByTurns(pointOfTrack);
    }

    public void closeOpenedZones() {
        if (isZoneByDropTimeCreation){
            zoneByDropTimeGenerator
                    .createPossibleZoneFromRemainingData()
                    .ifPresent(zone -> {
                        zonesByDropTime.add(zone);
                        System.out.println("LAST by time: " + zone);
                    });
        }
        if (isZoneByPointsCreation) {
            zoneByPointsGenerator
                    .createPossibleZoneFromRemainingData()
                    .ifPresent(zone -> {
                        zonesByPoints.add(zone);
                        System.out.println("LAST by point: " + zone);
                    });
        }
        if (isZoneByTurnsCreation) {
            zoneByTurnsGenerator
                    .createPossibleZoneFromRemainingData()
                    .ifPresent(zone -> {
                        zonesByTurns.add(zone);
                        System.out.println("LAST by turn: " + zone);
                    });
        }
    }

    public Map<TypeOfZone, List<ForbiddenZone>> getMapOfZonesCreatedFromBuffer() {
        System.out.println(zonesMap);
        return zonesMap;
    }

    private void initZonesToCreateConfig() {
        this.isZoneByDropTimeCreation =
                track.getProcessingConfig().isZoneByDropTimeCreation();
        this.isZoneByPointsCreation =
                track.getProcessingConfig().isZoneByPointsCreation();
        this.isZoneByTurnsCreation =
                track.getProcessingConfig().isZoneByTurnsCreation();
    }

    private void initPointsMultiplicationConfig() {
        this.isPointsMultiplication =
                track.getZoneByPointsConfig().isPointsMultiplication();
    }

    private void initGenerators() {
        if (isZoneByDropTimeCreation) {
            zoneByDropTimeGenerator =
                    new ZoneByDropTimeGenerator(
                            track.getDropStartTime(),
                            track.getDropStopTime());
        }
        if (isZoneByPointsCreation) {
            zoneByPointsGenerator =
                    new ZoneByPointsGenerator(
                            track.getZoneByPointsConfig(),
                            track.getMapConfig());
            if (isPointsMultiplication)
                multipliedPointsGenerator = new MultipliedPointsGenerator();
        }
        if (isZoneByTurnsCreation) {
            turnOfTrackGenerator = new TurnOfTrackGenerator(new TurnAngleCalculator());
            zoneByTurnsGenerator =
                    new ZoneByTurnsGenerator(track.getZoneByTurnsConfig());
        }
    }

    private void initZonesMap() {
        zonesMap.put(TypeOfZone.BY_DROP_TIME, zonesByDropTime);
        zonesMap.put(TypeOfZone.BY_POINTS, zonesByPoints);
        zonesMap.put(TypeOfZone.BY_TURNS, zonesByTurns);
    }

    private void createZonesByDropTime(PointOfTrack pointOfTrack) {
        zoneByDropTimeGenerator.updatePointsBuffer(pointOfTrack);
        zoneByDropTimeGenerator
                .createZoneFromBuffer()
                .ifPresent(zone -> {
                    zonesByDropTime.add(zone);
                    System.out.println("by time: " + zone);
                });
    }

    private void createZonesByPoints(PointOfTrack pointOfTrack) {
        if (isPointsMultiplication) {
            multipliedPointsGenerator.updateBuffer(pointOfTrack);
            multipliedPointsGenerator
                    .getMultipliedPoints()
                    .forEach(point -> {
                        zoneByPointsGenerator.updatePointsBuffer(point);
                        zoneByPointsGenerator
                                .createZoneFromBuffer()
                                .ifPresent(zone -> {
                                    zonesByPoints.add(zone);
                                    System.out.println("by point " + zone);
                                });
                    });
        } else {
            zoneByPointsGenerator.updatePointsBuffer(pointOfTrack);
            zoneByPointsGenerator
                    .createZoneFromBuffer()
                    .ifPresent(zone -> {
                        zonesByPoints.add(zone);
                        System.out.println("by point " + zone);
                    });
        }
    }

    private void createZonesByTurns(PointOfTrack pointOfTrack) {
        turnOfTrackGenerator.updatePointsBuffer(pointOfTrack);
        turnOfTrackGenerator
                .createTurnFromBuffer()
                .ifPresent(turn -> {
                    zoneByTurnsGenerator.updateTurnsBuffer(turn);
                    zoneByTurnsGenerator
                            .createZoneFromBuffer()
                            .ifPresent(zone -> {
                                zonesByTurns.add(zone);
                                System.out.println("by turn " + zone);
                            });
                });
    }
}
