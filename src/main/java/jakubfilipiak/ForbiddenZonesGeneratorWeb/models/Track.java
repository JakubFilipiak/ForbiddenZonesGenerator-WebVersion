package jakubfilipiak.ForbiddenZonesGeneratorWeb.models;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.*;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.TypeOfZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.helpers.ForbiddenZone;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Jakub Filipiak on 29.05.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tracks")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_track")
    private Long id;

    @Column(name = "track_name", nullable = false, unique = true)
    private String trackName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_map_config")
    private MapConfig mapConfig;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_zone_by_points_config")
    private ZoneByPointsConfig zoneByPointsConfig;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_zone_by_turns_config")
    private ZoneByTurnsConfig zoneByTurnsConfig;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_zone_by_points_time_config")
    private ZoneByPointsTimeConfig zoneByPointsTimeConfig;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_zone_by_turns_time_config")
    private ZoneByTurnsTimeConfig zoneByTurnsTimeConfig;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_processing_config", nullable = false)
    private ProcessingConfig processingConfig;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_track_file", nullable = false)
    private LocalFile trackFile;

    @Column(name = "drop_start_time")
    private LocalTime dropStartTime;
    @Column(name = "drop_stop_time")
    private LocalTime dropStopTime;

    @Transient
    private Map<TypeOfZone, List<ForbiddenZone>> zonesMap;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_output_file")
    private LocalFile outputFile;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_output_debug_file")
    private LocalFile outputFileInDebugMode;

    @Column(name = "verified", nullable = false)
    @Builder.Default
    private boolean verified = false;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @Column(name = "processed", nullable = false)
    @Builder.Default
    private boolean processed = false;
}
