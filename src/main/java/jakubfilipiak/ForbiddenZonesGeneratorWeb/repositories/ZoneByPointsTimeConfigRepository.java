package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ZoneByPointsTimeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 14.07.2019.
 */
@Repository
public interface ZoneByPointsTimeConfigRepository extends JpaRepository<ZoneByPointsTimeConfig, Long> {

    @Query("select z from ZoneByPointsTimeConfig z where z.deleted = false")
    List<ZoneByPointsTimeConfig> findAllNotDeleted();

    @Query("select z from ZoneByPointsTimeConfig z where z.deleted = false and z" +
            ".verified = true")
    List<ZoneByPointsTimeConfig> findAllNotDeletedAndVerified();

    @Query("select z from ZoneByPointsTimeConfig z where z.configName = ?1")
    Optional<ZoneByPointsTimeConfig> findByConfigName(String configName);
}
