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

    @Column(name = "min_turn_initiation_angle", nullable = false)
    @Builder.Default
    private int minTurnInitiationAngle = 0;

    @Column(name = "ignore_turns", nullable = false)
    private boolean ignoreTurns;
    @Column(name = "ignored_turn_min_value")
    @Builder.Default
    private int ignoredTurnMinValue = 0;
    @Column(name = "ignored_turn_max_value")
    @Builder.Default
    private int ignoredTurnMaxValue = 0;

    @Column(name = "min_turns_number_in_series", nullable = false)
    @Builder.Default
    private int minTurnsNumberInSeries = - 1;
    @Column(name = "max_pauses_number_between_turns", nullable = false)
    @Builder.Default
    private int maxPausesNumberBetweenTurns = - 1;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "zoneByTurnsConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
