package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 19.06.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "zone_by_turns_configs")
public class ZoneByTurnsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zone_by_turns_config")
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    // turn

    @Column(name = "min_turn_initiation_angle", nullable = false)
    private int minTurnInitiationAngle;

    @Column(name = "ignored_turn_min_value")
    private int ignoredTurnMinValue;
    @Column(name = "ignored_turn_max_value")
    private int ignoredTurnMaxValue;

    // zone forbidden by turns

    @Column(name = "min_turns_number_in_series", nullable = false)
    private int minTurnsNumberInSeries;
    @Column(name = "max_pauses_number_between_turns", nullable = false)
    private int maxPausesNumberBetweenTurns;

    @Column(name = "single_turn_zone_full_time", nullable = false)
    private boolean singleTurnZoneFullTime;
    @Column(name = "single_turn_zone_begin_offset", nullable = false)
    private int singleTurnZoneBeginOffset;
    @Column(name = "single_turn_zone_end_offset", nullable = false)
    private int singleTurnZoneEndOffset;

    @Column(name = "group_of_turns_zone_full_time", nullable = false)
    private boolean groupOfTurnsZoneFullTime;
    @Column(name = "group_of_turns_zone_begin_offset", nullable = false)
    private int groupOfTurnsZoneBeginOffset;
    @Column(name = "group_of_turns_zone_end_offset", nullable = false)
    private int groupOfTurnsZoneEndOffset;

    // properties

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    // relation

    @OneToMany(mappedBy = "zoneByTurnsConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
