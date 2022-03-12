package app.services.booking;

import app.entities.booking.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createOrUpdateBooking(Booking booking);
    Optional<Booking> findById(Long id);
    void deleteBooking(Booking booking);
    List<Booking> findAllBookings();
}
