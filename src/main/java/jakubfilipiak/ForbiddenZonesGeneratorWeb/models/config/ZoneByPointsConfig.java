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
@Table(name = "zone_by_points_configs")
public class ZoneByPointsConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zone_by_points_config")
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    // point

    @Column(name = "points_multiplication", nullable = false)
    private boolean pointsMultiplication;

    @Column(name = "point_neighborhood_verification", nullable = false)
    private boolean pointNeighborhoodVerification;
    @Column(name = "radius_of_pixels_to_be_verified")
    private int radiusOfPixelsToBeVerified;

    // zone forbidden by points

    @Column(name = "min_points_number_in_series", nullable = false)
    private int minPointsNumberInSeries;
    @Column(name = "max_pauses_number_between_points", nullable = false)
    private int maxPausesNumberBetweenPoints;

    @Column(name = "single_point_zone_begin_offset", nullable = false)
    private int singlePointZoneBeginOffset;
    @Column(name = "single_point_zone_end_offset", nullable = false)
    private int singlePointZoneEndOffset;

    @Column(name = "group_of_points_zone_begin_offset", nullable = false)
    private int groupOfPointsZoneBeginOffset;
    @Column(name = "group_of_points_zone_end_offset", nullable = false)
    private int groupOfPointsZoneEndOffset;

    // properties

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    // relation

    @OneToMany(mappedBy = "zoneByPointsConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
