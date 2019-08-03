package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.crypto.CustomSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Jakub Filipiak on 30.07.2019.
 */
@Repository
public interface CustomSecretKeyRepository extends JpaRepository<CustomSecretKey, Long> {

    @Query("select c from CustomSecretKey c where c.keyName = ?1")
    Optional<CustomSecretKey> findByKeyName(String keyName);
}
