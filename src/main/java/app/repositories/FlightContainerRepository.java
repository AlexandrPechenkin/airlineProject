package app.repositories;

import app.entities.FlightContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightContainerRepository extends JpaRepository<FlightContainer, Long> {
}
