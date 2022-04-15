package app.repositories;

import app.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /**
     * найти билет по номеру брони
     * @param holdNumber
     * @return {@link Ticket}
     */
    @Query(value = "select t from Ticket t where t.holdNumber = ?1")
    Ticket findTicketByHoldNumber(Long holdNumber);
}
