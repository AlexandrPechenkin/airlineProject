package app.services.impl;

import app.entities.flight.Flight;
import app.repositories.flight.FlightRepository;
import app.services.interfaces.FlightService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FligthServiceImpl implements FlightService {

    final
    FlightRepository flightRepository;

    public FligthServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }


    @Override
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Optional<Flight> findById(long id) {

        return flightRepository.findById(id);
    }
}
