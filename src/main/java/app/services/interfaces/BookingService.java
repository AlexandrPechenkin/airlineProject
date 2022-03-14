package app.services.interfaces;

import app.entities.booking.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking createOrUpdate(Booking booking);
    Optional<Booking> findById(Long id);
    void delete(Booking booking);
    void deleteIfExpired(Booking booking);
    List<Booking> findAll();
}
