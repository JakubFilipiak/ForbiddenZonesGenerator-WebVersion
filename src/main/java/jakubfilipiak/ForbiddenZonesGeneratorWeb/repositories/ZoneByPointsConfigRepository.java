package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 21.06.2019.
 */
@Repository
public interface ZoneByPointsConfigRepository extends JpaRepository<ZoneByPointsConfig,
        Long> {

    @Query("select z from ZoneByPointsConfig z where z.deleted = false")
    List<ZoneByPointsConfig> findAllNotDeleted();

    @Query("select z from ZoneByPointsConfig z where z.configName = ?1")
    Optional<ZoneByPointsConfig> findByConfigName(String configName);
}
