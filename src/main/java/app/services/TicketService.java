package app.services;

import app.entities.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> getAllTickets();
    void removeTicket(Ticket ticket);
    void createTicket(Ticket ticket);
    Ticket getTicketByID(Long id);
    List<Ticket> findTickets(String origin, String destination);
    List<Ticket> findTickets(String origin, String destination, String departureDate);
    List<Ticket> findTickets(String origin, String destination, String departureDate, String arrivalDate);
}
