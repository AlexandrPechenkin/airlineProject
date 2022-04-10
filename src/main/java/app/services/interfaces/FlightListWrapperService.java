package app.services.interfaces;

import app.entities.FlightListWrapper;

import java.util.Optional;

public interface FlightListWrapperService {
    FlightListWrapper createOrUpdateFlightListWrapper(FlightListWrapper flightListWrapper);
    Optional<FlightListWrapper> findById(long id);
}
