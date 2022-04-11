package app.services.interfaces;

import app.entities.DestinationResource;

import java.util.List;
import java.util.Optional;

public interface DestinationResourceService {
    DestinationResource createOrUpdateDestinationResource(DestinationResource destinationResource);
    Optional<DestinationResource> findById(long id);
    List<DestinationResource> findByCity(String cityName);
    DestinationResource findByBaseAirportCode(String baseCode);
    List<DestinationResource> findAll();
    void deleteDestinationResourceById(long id);
    void addCodeToAvailable(long id, String airportCode);
    void removeCodeFromAvailable(long id, String airportCode);
}
