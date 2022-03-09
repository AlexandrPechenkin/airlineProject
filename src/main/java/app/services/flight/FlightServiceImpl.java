package app.services.flight;

import app.entities.flight.Flight;
import app.repositories.flight.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     * получение всех перелетов
     */
    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    /**
     * создание перелета
     */
    @Override
    public void createFlight(Flight flight) {
        flightRepository.save(flight);
    }

    /**
     * удаление перелета
     */
    @Override
    public void removeFlight(Flight flight) {
        flightRepository.delete(flight);
    }

    /**
     * получение перелета по id
     */
    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.getById(id);
    }
}
