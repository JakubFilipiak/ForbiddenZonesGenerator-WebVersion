package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import java.util.List;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
public class Track {

    private String trackFilePathname;

    private List<ForbiddenZone> forbiddenZonesByDrop;
    private List<ForbiddenZone> forbiddenZonesByColor;
    private List<ForbiddenZone> forbiddenZonesByTurns;
    private List<ForbiddenZone> mergedForbiddenZones;

    public Track(String trackFilePathname) {
        this.trackFilePathname = trackFilePathname;
    }

    public String getTrackFilePathname() {
        return trackFilePathname;
    }

    public List<ForbiddenZone> getForbiddenZonesByDrop() {
        return forbiddenZonesByDrop;
    }

    public void setForbiddenZonesByDrop(List<ForbiddenZone> forbiddenZonesByDrop) {
        this.forbiddenZonesByDrop = forbiddenZonesByDrop;
    }

    public List<ForbiddenZone> getForbiddenZonesByColor() {
        return forbiddenZonesByColor;
    }

    public void setForbiddenZonesByColor(List<ForbiddenZone> forbiddenZonesByColor) {
        this.forbiddenZonesByColor = forbiddenZonesByColor;
    }

    public List<ForbiddenZone> getForbiddenZonesByTurns() {
        return forbiddenZonesByTurns;
    }

    public void setForbiddenZonesByTurns(List<ForbiddenZone> forbiddenZonesByTurns) {
        this.forbiddenZonesByTurns = forbiddenZonesByTurns;
    }

    public List<ForbiddenZone> getMergedForbiddenZones() {
        return mergedForbiddenZones;
    }

    public void setMergedForbiddenZones(List<ForbiddenZone> mergedForbiddenZones) {
        this.mergedForbiddenZones = mergedForbiddenZones;
    }
}
