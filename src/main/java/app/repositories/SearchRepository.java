package app.repositories;

import app.entities.Flight;
import app.entities.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    @Query(value = "select f from Flight f where (f.destinationFrom like concat('%', :destinationFrom, '%')) " +
            "and f.destinationTo like  concat('%', :destinationTo, '%') " +
            "and f.departureDate like concat('%', :departureDate, '%')")
    List<Flight> findFlightsByRoute(String destinationFrom, String destinationTo, LocalDate departureDate);
}
