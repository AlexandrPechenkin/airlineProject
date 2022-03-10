package app.services.search;

import app.entities.ticket.Ticket;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Ticket> findTickets(String route, LocalDate departureDate);
}
