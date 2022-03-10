package app.services.flight;

import app.entities.flight.Flight;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();

    void createOrUpdateFlight(Flight flight);

    void removeFlight(Flight flight);

    Flight getFlightById(Long id);

    List<Flight> findFlights(String destinationFrom, String destinationTo, LocalDate departureDate);

}
