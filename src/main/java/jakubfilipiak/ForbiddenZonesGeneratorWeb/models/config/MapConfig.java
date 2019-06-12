package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import lombok.*;

import javax.persistence.*;
import java.awt.*;

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
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    @Column(name = "file_pathname", nullable = false)
    private String filePathname;

    @Column(name = "allowed_color", nullable = false)
    private Color allowedColor;
    @Column(name = "forbidden_color", nullable = false)
    private Color forbiddenColor;

    @Column(name = "bottom_left_corner_latitude", nullable = false)
    private float bottomLeftCornerLatitude;
    @Column(name = "bottom_left_corner_longitude", nullable = false)
    private float bottomLeftCornerLongitude;

    @Column(name = "upper_right_corner_latitude", nullable = false)
    private float upperRightCornerLatitude;
    @Column(name = "upper_right_corner_longitude", nullable = false)
    private float upperRightCornerLongitude;
}
