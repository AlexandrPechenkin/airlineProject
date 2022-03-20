package app.services.ticket;

import app.entities.ticket.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    List<Ticket> getAllTickets();

    void removeTicket(Ticket ticket);

    Ticket createOrUpdateTicket(Ticket ticket);

    Optional<Ticket> getTicketById(Long id);
}
