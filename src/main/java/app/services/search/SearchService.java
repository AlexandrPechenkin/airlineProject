package app.services.search;

import app.entities.route.Route;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    List<Route> findTickets(String from, String to, LocalDate departureDate);
}
