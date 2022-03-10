package app.repositories.search;

import app.entities.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SearchRepository extends JpaRepository<Ticket, Long> {
    /**
     * поиск в бд по маршруту и дате вылета
     */
    @Query(value = "select t from Ticket t where (t.flight.route.destinationFrom like concat('%', :destinationFrom, '%')) " +
            "and t.flight.route.destinationTo like  concat('%', :destinationTo, '%') " +
            "and t.flight.route.departureDate like concat('%', :departureDate, '%')")
    List<Ticket> findTickets(String destinationFrom, String destinationTo, LocalDate departureDate);
}
