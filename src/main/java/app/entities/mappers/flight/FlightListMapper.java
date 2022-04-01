package app.entities.mappers.flight;

import app.entities.Flight;
import app.entities.dtos.FlightDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер для списка рейсов
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface FlightListMapper {

    /**
     * Возвращает список DTO рейсов по списку рейсов
     * @param flightList - список рейсов
     * @return - список DTO рейсов
     */
    List<FlightDTO> toDTOList(List<Flight> flightList);

    /**
     * Возвращает список рейсов по списку DTO рейсов
     * @param flightDTOList - список DTO рейсов
     * @return - список рейсов
     */
    List<Flight> toEntityList(List<FlightDTO> flightDTOList);
}
