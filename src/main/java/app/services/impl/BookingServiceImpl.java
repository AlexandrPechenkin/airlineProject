package app.services.impl;

import app.entities.Booking;
import app.entities.FlightContainer;
import app.entities.Ticket;
import app.repositories.BookingRepository;
import app.services.interfaces.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    public List<Booking> constructBookingsByFlightContainer(FlightContainer container) {
        List<Booking> bookings = new ArrayList<>();
        // логика установки цены в зависимости от категории или изначальная установка цены
        container.getFlights().forEach(listWrapper -> {
            listWrapper.getAllFlightsFromToCities().forEach(flight -> {
                Long holdNumber = generateHoldNumber();
                bookings.add(
                    Booking.builder()
                        .category(container.getCategory())
                        .initialBookingDateTime(LocalDateTime.now())
                        .holdNumber(holdNumber)
                        .departTicket(
                                Ticket.builder()
                                    .flight(flight)
                                    .holdNumber(holdNumber)
                                    .price(10000L)
                                    .build())
                        .status("IN_PROGRESS")
                    .build());
            });
        });
        return bookings;
    }

    private Long generateHoldNumber() {
        return Long.parseLong(("" + (UUID.randomUUID() + "").hashCode()).replaceAll("-", ""));
    }

    @Override
    public Booking createOrUpdateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> findById(long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Booking> findByHoldNumber(long holdNumber) {
        return bookingRepository.findByHoldNumber(holdNumber);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteBookingById(long id) {
        bookingRepository.deleteById(id);
    }
}
