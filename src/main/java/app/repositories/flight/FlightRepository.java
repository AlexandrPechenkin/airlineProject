package app.repositories.flight;

import app.entities.flight.Flight;
import app.entities.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>  {
}
