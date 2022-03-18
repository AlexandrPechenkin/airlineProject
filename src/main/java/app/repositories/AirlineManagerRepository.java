package app.repositories;

import app.entities.AirlineManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineManagerRepository extends JpaRepository<AirlineManager, Long> {
}
