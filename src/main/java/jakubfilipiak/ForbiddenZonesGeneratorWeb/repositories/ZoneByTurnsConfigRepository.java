package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByTurnsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 20.06.2019.
 */
@Repository
public interface ZoneByTurnsConfigRepository extends JpaRepository<ZoneByTurnsConfig,
        Long> {

    @Query("select z from ZoneByTurnsConfig z where z.deleted = false")
    List<ZoneByTurnsConfig> findAllNotDeleted();

    @Query("select z from ZoneByTurnsConfig z where z.configName = ?1")
    Optional<ZoneByTurnsConfig> findByConfigName(String configName);
}
