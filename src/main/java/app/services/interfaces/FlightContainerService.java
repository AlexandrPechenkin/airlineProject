package app.services.interfaces;

import app.entities.FlightContainer;

import java.util.Optional;

public interface FlightContainerService {

    FlightContainer createOrUpdateFlightContainer(FlightContainer flightContainer);
    Optional<FlightContainer> findById(long id);

}
