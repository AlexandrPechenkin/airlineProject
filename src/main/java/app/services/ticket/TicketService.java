package app.services.ticket;

import app.entities.ticket.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();

    void removeTicket(Ticket ticket);

    void createTicket(Ticket ticket);

    Ticket getTicketByID(Long id);

    List<Ticket> findTickets(String route, LocalDate departureDate);
}
