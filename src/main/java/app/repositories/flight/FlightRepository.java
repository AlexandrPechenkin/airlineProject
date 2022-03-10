package app.repositories.flight;

import app.entities.flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * поиск по месту вылета, месту прилета и дате вылета
     *
     * @param from          место вылета
     * @param to            место прилета
     * @param departureDate дата вылета
     * @return
     */
    @Query(value = "select f from Flight f where (f.from like concat('%', :from, '%')) " +
            "and f.to like  concat('%', :to, '%') " +
            "and f.departureDate like concat('%', :departureDate, '%')")
    List<Flight> findFlights(String from, String to, LocalDate departureDate);

}
