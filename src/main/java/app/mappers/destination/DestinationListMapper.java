package app.mappers.destination;

import app.entities.destination.Destination;
import app.entities.destination.dto.DestinationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер для списка аэропортов
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DestinationListMapper {

    /**
     * Возвращает список DTO аэропортов по списку аэропортов
     * @param destinationList - список аэропортов
     * @return - список DTO аэропортов
     */
    List<DestinationDTO> toDTOList(List<Destination> destinationList);

    /**
     * Возвращает список аэропортов по списку DTO аэропортов
     * @param destinationDTOList - список DTO аэропортов
     * @return - список аэропортов
     */
    List<Destination> toEntityList(List<DestinationDTO> destinationDTOList);
}
