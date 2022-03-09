package app.services.flight;

import app.entities.flight.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();
    void createFlight(Flight flight);
    void removeFlight(Flight flight);
    Flight getFlightById(Long id);

}
