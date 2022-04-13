package app.entities.mappers.booking;

import app.entities.Booking;
import app.entities.dtos.BookingDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс для преобразования классов {@link List<Booking>} и {@link List<BookingDTO>} друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookingListMapper {

    List<BookingDTO> toDto(List<Booking> bookings);
    List<Booking> toEntity(List<BookingDTO> bookingDTOS);
}
