package app.mappers.flight;

import app.entities.category.Category;
import app.entities.category.dto.CategoryDTO;
import app.entities.flight.Flight;
import app.entities.flight.dto.FlightDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = { app.mappers.flight.FlightMapper.class })
public interface FlightMapper {
    /**
     * Метод сопоставляет Seat c SeatDTO
     *
     * @param flight - категория
     */
    FlightDTO toDto(Flight flight);

    /**
     * Метод сопоставляет SeatDTO c Seat
     *
     * @param flightDTO - Поля места которые отдаются наружу
     */
    Flight toEntity(FlightDTO flightDTO);
}