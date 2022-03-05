package app.services;

import app.entities.Ticket;

import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    void removeTicket(Ticket ticket);
    void createTicket(Ticket ticket);
    Ticket getTicketByID(Long id);
    List<Ticket> findTicketsByOrigin(String origin);
    List<Ticket> findTicketsByDestination(String destination);
    List<Ticket> findTicketsByOriginAndDestination(String origin, String destination);
    List<Ticket> findTickets(String origin, String destination, GregorianCalendar departureDate);
}
