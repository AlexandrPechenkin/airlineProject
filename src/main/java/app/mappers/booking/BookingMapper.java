package app.mappers.booking;

import app.entities.booking.Booking;
import app.entities.booking.dto.BookingDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookingMapper {
    BookingDTO toDto(Booking booking);
    Booking toEntity(BookingDTO bookingDTO);
}
