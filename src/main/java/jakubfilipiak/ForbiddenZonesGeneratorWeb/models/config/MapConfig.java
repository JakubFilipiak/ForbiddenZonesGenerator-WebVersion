package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "map_configs")
public class MapConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "file_pathname", nullable = false)
    private String filePathname;

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

    @Column(name = "verified", nullable = false)
    private boolean verified = false;
}
