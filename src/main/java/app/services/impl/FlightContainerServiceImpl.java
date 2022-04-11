package app.services.impl;

import app.entities.FlightContainer;
import app.repositories.FlightContainerRepository;
import app.services.interfaces.FlightContainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class FlightContainerServiceImpl implements FlightContainerService {
    private final FlightContainerRepository flightContainerRepository;

    @Override
    public FlightContainer createOrUpdateFlightContainer(FlightContainer flightContainer) {
        return flightContainerRepository.save(flightContainer);
    }

    @Override
    public Optional<FlightContainer> findById(long id) {
        return flightContainerRepository.findById(id);
    }
}
