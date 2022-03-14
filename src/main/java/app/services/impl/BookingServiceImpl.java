package app.services.impl;

import app.entities.booking.Booking;
import app.repositories.booking.BookingRepository;
import app.services.interfaces.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createOrUpdate(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    @Override
    public void deleteIfExpired(Booking booking) {
        long diffHours = Math.abs(Duration.between(LocalDateTime.now(), booking.getInitialBookingDateTime()).toHours());
        switch(booking.getStatus()) {
            case "PAYMENT":
                if (diffHours > 1) {
                    delete(booking);
                }
                break;
            case "BOOKED":
                if (diffHours > 3) {
                    delete(booking);
                }
                break;
            case "CANCELLED":
                delete(booking);
                break;
            case "PAID":
            case "IN_PROGRESS":
                break;
        }
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
}
