package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.storage.LocalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Jakub Filipiak on 19.06.2019.
 */
@Repository
public interface LocalFileRepository extends JpaRepository<LocalFile, Long> {

    @Query("select l from LocalFile l where l.pathName = ?1")
    Optional<LocalFile> findByUniquePathname(String uniquePathname);

    @Query("select l from LocalFile l where l.uniqueName = ?1")
    Optional<LocalFile> findByUniqueName(String uniqueName);
}
