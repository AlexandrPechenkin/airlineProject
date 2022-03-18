package app.services.impl;

import app.entities.AirlineManager;
import app.repositories.AirlineManagerRepository;
import app.services.interfaces.AirlineManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirlineManagerServiceImpl implements AirlineManagerService {
    private final AirlineManagerRepository airlineManagerRepository;

    @Override
    public AirlineManager createOrUpdateAirlineManager(AirlineManager airlineManager) {
        return airlineManagerRepository.save(airlineManager);
    }

    @Override
    public Optional<AirlineManager> findById(Long id) {
        return airlineManagerRepository.findById(id);
    }

    @Override
    public List<AirlineManager> findAll() {
        return airlineManagerRepository.findAll();
    }

    @Override
    public void deleteAirlineManager(AirlineManager airlineManager) {
        airlineManagerRepository.delete(airlineManager);
    }
}
