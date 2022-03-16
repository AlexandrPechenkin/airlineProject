package app.services.interfaces;

import app.entities.flight.Flight;

import java.util.Optional;

public interface FlightService {
    Flight addFlight(Flight flight);
    Optional<Flight> findById(long id);
}
