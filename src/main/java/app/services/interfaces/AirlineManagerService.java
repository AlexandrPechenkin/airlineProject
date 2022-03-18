package app.services.interfaces;

import app.entities.AirlineManager;

import java.util.List;
import java.util.Optional;

public interface AirlineManagerService {
    AirlineManager createOrUpdateAirlineManager(AirlineManager airlineManager);
    Optional<AirlineManager> findById(Long id);
    List<AirlineManager> findAll();
    void deleteAirlineManager(AirlineManager airlineManager);
}
