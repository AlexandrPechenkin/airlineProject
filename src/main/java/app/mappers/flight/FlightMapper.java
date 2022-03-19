package app.mappers.flight;

import app.entities.flight.Flight;
import app.entities.flight.dto.FlightDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {app.mappers.flight.FlightMapper.class})
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

    /**
     * Метод сопоставляет List Flight с List FlightDTO
     *
     * @param flight - лист перелетов
     * @return
     */
    List<FlightDTO> listToDTO(List<Flight> flight);

    /**
     * Метод сопоставляет List FlightDTO с Flight
     *
     * @param flightDTOS - лист с полями перелета, которые отдаются наружу
     * @return
     */
    List<Flight> listToEntity(List<FlightDTO> flightDTOS);
}
