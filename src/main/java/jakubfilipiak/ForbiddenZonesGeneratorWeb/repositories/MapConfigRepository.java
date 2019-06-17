package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.MapConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Jakub Filipiak on 13.06.2019.
 */
@Repository
public interface MapConfigRepository extends JpaRepository<MapConfig, Long> {

    @Query("select m from MapConfig m where m.configName = ?1")
    Optional<MapConfig> findByConfigName(String configName);

    @Transactional
    @Modifying
    @Query("delete from MapConfig m where m.configName = ?1")
    void deleteByConfigName(String configName);
}
