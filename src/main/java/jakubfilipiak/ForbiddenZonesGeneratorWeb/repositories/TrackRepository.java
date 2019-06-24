package jakubfilipiak.ForbiddenZonesGeneratorWeb.repositories;

import jakubfilipiak.ForbiddenZonesGeneratorWeb.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jakub Filipiak on 18.06.2019.
 */
@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    @Query("select t from Track t where t.deleted = false")
    List<Track> findAllNotDeleted();

    @Query("select t from Track t where t.trackName = ?1")
    Optional<Track> findByTrackName(String trackName);
}
