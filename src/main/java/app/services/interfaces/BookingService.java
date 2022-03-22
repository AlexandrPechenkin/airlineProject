package app.services.interfaces;


import app.entities.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    Booking createOrUpdateBooking(Booking booking);
    Optional<Booking> findById(long id);
    List<Booking> findAll();
    void deleteBookingById(long id);

}
