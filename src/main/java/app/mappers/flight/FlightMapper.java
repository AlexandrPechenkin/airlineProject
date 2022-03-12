package app.mappers.flight;

import app.entities.flight.Flight;
import app.entities.flight.dto.FlightDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FlightMapper {
    /**
     * Метод сопоставляет Flight c FlightDTO
     *
     * @param flight - перелет
     */
    FlightDTO toDTO(Flight flight);

    /**
     * Метод сопоставляет FlightDTO c Flight
     *
     * @param flightDTO - Поля перелета которые отдаются наружу
     */
    Flight toEntity(FlightDTO flightDTO);
}
