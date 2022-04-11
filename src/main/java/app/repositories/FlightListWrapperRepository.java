package app.repositories;

import app.entities.FlightListWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightListWrapperRepository extends JpaRepository<FlightListWrapper, Long> {
}
