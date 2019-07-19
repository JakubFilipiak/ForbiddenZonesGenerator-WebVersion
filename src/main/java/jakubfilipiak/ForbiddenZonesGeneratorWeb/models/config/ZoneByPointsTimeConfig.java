package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 13.07.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "zone_by_points_time_configs")
public class ZoneByPointsTimeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zone_by_points_time_config")
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    @Column(name = "single_point_zone_begin_offset", nullable = false)
    private int singlePointZoneBeginOffset;
    @Column(name = "single_point_zone_end_offset", nullable = false)
    private int singlePointZoneEndOffset;

    @Column(name = "group_of_points_zone_begin_offset", nullable = false)
    private int groupOfPointsZoneBeginOffset;
    @Column(name = "group_of_points_zone_end_offset", nullable = false)
    private int groupOfPointsZoneEndOffset;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(mappedBy = "zoneByPointsTimeConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
