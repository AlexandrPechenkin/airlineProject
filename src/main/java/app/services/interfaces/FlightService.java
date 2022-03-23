package app.services.interfaces;

import app.entities.Destination;
import app.entities.Flight;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    List<Flight> getAllFlights();

    Flight createOrUpdateFlight(Flight flight);

    void removeFlight(Flight flight);

    Optional<Flight> getFlightById(Long id);

    List<Flight> findFlights(String cityFrom, String cityTo, LocalDate departureDate);

}
