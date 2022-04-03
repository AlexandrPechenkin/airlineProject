package app.services.interfaces;


import app.entities.Flight;
import app.entities.Search;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SearchService {
    List<Search> getAll();
    Optional<Search> getById(Long Id);
    List<Flight> findFlightsByRoute(String destinationFrom, String destinationTo, LocalDate departureDate);
}
