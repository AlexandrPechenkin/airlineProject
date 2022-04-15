package app.entities.mappers.booking;

import app.entities.Booking;
import app.entities.dtos.BookingDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов Booking и BookingDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookingMapper {
    /**
     * Приведение Booking к BookingDTO.
     *
     * @param booking - данные, которые передаются с бэкенда.
     * @return {@link BookingDTO}
     */
    BookingDTO toDto(Booking booking);

    /**
     * Приведение BookingDTO к Booking.
     *
     * @param bookingDTO - данные, которые передаются с фронтенда.
     * @return {@link Booking}
     */
    Booking toEntity(BookingDTO bookingDTO);
}