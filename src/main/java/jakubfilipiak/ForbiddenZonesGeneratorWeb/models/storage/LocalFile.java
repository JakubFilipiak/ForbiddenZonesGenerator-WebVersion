package jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jakub Filipiak on 13.06.2019.
 */
@Getter
@Setter
public class LocalFile {

    private String originalName;
    private String uniquePathname;

    public LocalFile(String originalName, String uniquePathname) {
        this.originalName = originalName;
        this.uniquePathname = uniquePathname;
    }
}
