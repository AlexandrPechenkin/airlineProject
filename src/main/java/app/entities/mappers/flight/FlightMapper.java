package app.entities.mappers.flight;

import app.entities.Flight;
import app.entities.dtos.FlightDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = { app.entities.mappers.flight.FlightMapper.class })
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