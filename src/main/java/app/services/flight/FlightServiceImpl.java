package app.services.flight;

import app.entities.flight.Flight;
import app.repositories.flight.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public void createFlight(Flight flight) {
        flightRepository.save(flight);
    }

    @Override
    public void removeFlight(Flight flight) {
        flightRepository.delete(flight);
    }

    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.getById(id);
    }
}
