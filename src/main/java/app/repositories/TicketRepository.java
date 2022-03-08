package app.repositories;

import app.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     *     поиск в бд по маршруту и дате вылета
     */
    @Query(value = "select t from Ticket t where (t.route like concat('%', :route, '%')) " +
            "and t.departureDate like concat('%', :departureDate, '%')")
    List<Ticket> findTickets(String route, LocalDate departureDate);

}
