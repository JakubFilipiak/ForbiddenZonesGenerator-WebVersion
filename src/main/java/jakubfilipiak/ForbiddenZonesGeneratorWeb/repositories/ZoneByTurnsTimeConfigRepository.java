package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsTimeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 15.07.2019.
 */
@Repository
public interface ZoneByTurnsTimeConfigRepository extends JpaRepository<ZoneByTurnsTimeConfig, Long> {

    @Query("select z from ZoneByTurnsTimeConfig z where z.deleted = false")
    List<ZoneByTurnsTimeConfig> findAllNotDeleted();

    @Query("select z from ZoneByTurnsTimeConfig z where z.deleted = false and z" +
            ".verified = true")
    List<ZoneByTurnsTimeConfig> findAllNotDeletedAndVerified();

    @Query("select z from ZoneByTurnsTimeConfig z where z.configName = ?1")
    Optional<ZoneByTurnsTimeConfig> findByConfigName(String configName);
}
