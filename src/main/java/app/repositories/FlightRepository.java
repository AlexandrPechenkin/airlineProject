package app.repositories;

import app.entities.Flight;
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
     * @param destinationFrom место вылета
     * @param destinationTo   место прилета
     * @param departureDate   дата вылета
     * @return
     */
    @Query(value = "select f from Flight f where (f.destinationFrom like concat('%', :destinationFrom, '%')) " +
            "and f.destinationTo like  concat('%', :destinationTo, '%') " +
            "and f.departureDate like concat('%', :departureDate, '%')")
    List<Flight> findFlights(String destinationFrom, String destinationTo, LocalDate departureDate);

}
