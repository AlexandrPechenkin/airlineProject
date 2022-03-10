package app.repositories.search;

import app.entities.route.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SearchRepository extends JpaRepository<Route, Long> {
    /**
     * поиск в бд по маршруту и дате вылета
     */
    @Query(value = "select r from Route r where (r.from like concat('%', :from, '%')) " +
            "and r.to like  concat('%', :to, '%') " +
            "and r.departureDate like concat('%', :departureDate, '%')")
    List<Route> findTickets(String from, String to, LocalDate departureDate);
}
