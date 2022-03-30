package app.services.impl;

import app.entities.DestinationResource;
import app.repositories.DestinationResourceRepository;
import app.services.interfaces.DestinationResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationResourceServiceImpl implements DestinationResourceService {
    private final DestinationResourceRepository destinationResourceRepository;

    @Override
    public DestinationResource createOrUpdateDestinationResource(DestinationResource destinationResource) {
        return destinationResourceRepository.save(destinationResource);
    }

    @Override
    public Optional<DestinationResource> findById(long id) {
        return destinationResourceRepository.findById(id);
    }

    @Override
    public void deleteDestinationResourceById(long id) {
        destinationResourceRepository.deleteById(id);
    }

    @Override
    public List<DestinationResource> findAll() {
        return destinationResourceRepository.findAll();
    }

    @Override
    public List<DestinationResource> findByCity(String cityName) {
        return destinationResourceRepository.findByCity(cityName);
    }

    @Override
    public DestinationResource findByBaseAirportCode(String baseAirportCode) {
        return destinationResourceRepository.findByBaseAirportCode(baseAirportCode);
    }

    @Override
    public void addCodeToAvailable(long id, String newAirportCode) {
        findById(id).ifPresent(res -> res.getAvailableAirportCodes().add(newAirportCode));
    }

    @Override
    public void removeCodeFromAvailable(long id, String airportCode) {
        findById(id).ifPresent(res -> res.getAvailableAirportCodes().remove(airportCode));
    }
}
