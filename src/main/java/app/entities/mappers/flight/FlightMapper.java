package app.entities.mappers.flight;

import app.entities.Flight;
import app.entities.dtos.FlightDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {FlightMapper.class})
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
     * @param flightDTO - Поля перелета, которые отдаются наружу
     */
    Flight toEntity(FlightDTO flightDTO);

}
