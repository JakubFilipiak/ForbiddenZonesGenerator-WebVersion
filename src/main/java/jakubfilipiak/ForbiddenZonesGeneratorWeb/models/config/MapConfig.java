package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "map_configs")
public class MapConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_map_config")
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_map_file", nullable = false)
    private LocalFile mapFile;

    @Column(name = "allowed_rgb_color", nullable = false)
    private String allowedRGBColor;
    @Column(name = "forbidden_rgb_color", nullable = false)
    private String forbiddenRGBColor;

    @Column(name = "bottom_left_corner_latitude", nullable = false)
    private double bottomLeftCornerLatitude;
    @Column(name = "bottom_left_corner_longitude", nullable = false)
    private double bottomLeftCornerLongitude;

    @Column(name = "upper_right_corner_latitude", nullable = false)
    private double upperRightCornerLatitude;
    @Column(name = "upper_right_corner_longitude", nullable = false)
    private double upperRightCornerLongitude;

    // properties

    @Column(name = "verified", nullable = false)
    @Builder.Default
    private boolean verified = false;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    // relation

    @OneToMany(mappedBy = "mapConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
