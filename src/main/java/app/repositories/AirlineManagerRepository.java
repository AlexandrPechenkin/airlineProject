package app.repositories;

import app.entities.AirlineManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для класса AirlineManager.
 */
@Repository
public interface AirlineManagerRepository extends JpaRepository<AirlineManager, Long> {
}
