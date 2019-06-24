package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.config.ProcessingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 12.06.2019.
 */
@Repository
public interface ProcessingConfigRepository extends JpaRepository<ProcessingConfig
        , Long> {

    @Query("select p from ProcessingConfig p where p.deleted = false")
    List<ProcessingConfig> findAllNotDeleted();

    @Query("select p from ProcessingConfig p where p.configName = ?1")
    Optional<ProcessingConfig> findByConfigName(String configName);
}
