package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 14.07.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "zone_by_turns_time_configs")
public class ZoneByTurnsTimeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zone_by_turns_time_config")
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    @Column(name = "single_turn_zone_full_time", nullable = false)
    @Builder.Default
    private boolean singleTurnZoneFullTime = true;
    @Column(name = "single_turn_zone_begin_offset", nullable = false)
    private int singleTurnZoneBeginOffset;
    @Column(name = "single_turn_zone_end_offset", nullable = false)
    private int singleTurnZoneEndOffset;

    @Column(name = "group_of_turns_zone_full_time", nullable = false)
    @Builder.Default
    private boolean groupOfTurnsZoneFullTime = true;
    @Column(name = "group_of_turns_zone_begin_offset", nullable = false)
    private int groupOfTurnsZoneBeginOffset;
    @Column(name = "group_of_turns_zone_end_offset", nullable = false)
    private int groupOfTurnsZoneEndOffset;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "zoneByTurnsTimeConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
