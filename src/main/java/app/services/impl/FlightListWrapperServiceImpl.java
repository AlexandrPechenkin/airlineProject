package app.services.impl;

import app.entities.FlightListWrapper;
import app.repositories.FlightListWrapperRepository;
import app.services.interfaces.FlightListWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FlightListWrapperServiceImpl implements FlightListWrapperService {
    private final FlightListWrapperRepository flightListWrapperRepository;

    @Override
    public FlightListWrapper createOrUpdateFlightListWrapper(FlightListWrapper flightListWrapper) {
        return flightListWrapperRepository.save(flightListWrapper);
    }

    @Override
    public Optional<FlightListWrapper> findById(long id) {
        return flightListWrapperRepository.findById(id);
    }
}
