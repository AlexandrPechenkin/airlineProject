package app.repositories;

import app.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    //поиск в бд по месту вылета, месту прилета и дате вылета
    @Query(value = "select t from Ticket t where (t.origin like concat('%', :origin, '%')) " +
            "and t.destination like concat('%', :destination, '%') " +
            "and t.departureDate like concat('%', :departureDate, '%')")
    List<Ticket> findTickets(String origin, String destination, String departureDate);

}
