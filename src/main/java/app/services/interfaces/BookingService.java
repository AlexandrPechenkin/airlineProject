package app.services.interfaces;


import app.entities.Booking;
import app.entities.FlightContainer;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Booking createOrUpdateBooking(Booking booking);
    List<Booking> constructBookingsByFlightContainer(FlightContainer container);
    Optional<Booking> findById(long id);
    List<Booking> findByHoldNumber(long holdNumber);
    List<Booking> findAll();
    void deleteBookingById(long id);

}
