package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
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
@Table(name = "processing_configs")
public class ProcessingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_processing_config")
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    // types of zones to be created

    @Column(name = "zone_by_drop_time_creation", nullable = false)
    private boolean zoneByDropTimeCreation;
    @Column(name = "zone_by_points_creation", nullable = false)
    private boolean zoneByPointsCreation;
    @Column(name = "zone_by_turns_creation", nullable = false)
    private boolean zoneByTurnsCreation;

    // properties

    @Column(name = "verified", nullable = false)
    @Builder.Default
    private boolean verified = true;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    // relation

    @OneToMany(mappedBy = "processingConfig", cascade = CascadeType.ALL)
    private List<Track> tracks;
}
