package app.services.interfaces;

import app.entities.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getAllFlights();

    Flight createOrUpdateFlight(Flight flight);

    void removeFlight(Flight flight);

    Optional<Flight> findById(Long id);

    List<Flight> findFlights(String cityFrom, String cityTo, LocalDate departureDate);

    List<Flight> findFlightsByDestination(String from, String to, LocalDate departureDate);

    List<Flight> findAllWithDepartureDateAfter(String cityFrom, String cityTo, LocalDate date);
}
