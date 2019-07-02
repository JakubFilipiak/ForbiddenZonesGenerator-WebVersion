package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Jakub Filipiak on 13.06.2019.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "files")
public class LocalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    private Long id;

    private String originalName;
    private String uniqueName;
    private String pathName;

    @OneToMany(mappedBy = "trackFile", cascade = CascadeType.ALL)
    private List<Track> tracks;

    @OneToMany(mappedBy = "outputFile", cascade = CascadeType.ALL)
    private List<Track> outputFiles;

    @OneToMany(mappedBy = "outputFileInDebugMode", cascade = CascadeType.ALL)
    private List<Track> outputFilesInDebugMode;

    @OneToMany(mappedBy = "mapFile", cascade = CascadeType.ALL)
    private List<MapConfig> mapConfigs;
}
