package app.entities.mappers.aircraft;

import app.entities.Aircraft;
import app.entities.Destination;
import app.entities.dtos.AircraftDTO;
import app.entities.dtos.DestinationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер для списка воздушных судов
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AircraftListMapper {

    /**
     * Возвращает список DTO воздушных судов по списку судов
     * @param aircraftList - список воздушных судов
     * @return - список DTO воздушных судов
     */
    List<AircraftDTO> toDTOList(List<Aircraft> aircraftList);

    /**
     * Возвращает список воздушных судов по списку DTO судов
     * @param aircraftDTOList - список DTO воздушных судов
     * @return - список воздушных судов
     */
    List<Aircraft> toEntityList(List<AircraftDTO> aircraftDTOList);
}
