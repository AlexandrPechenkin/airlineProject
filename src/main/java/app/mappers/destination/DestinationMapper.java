package app.mappers.destination;

import app.entities.destination.Destination;
import app.entities.destination.dto.DestinationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Маппер MapStruct для аэропорта
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DestinationMapper {

    /**
     * Возвращает объект DTO аэропорта по объекту аэропорта
     * @param destination - объект аэропорта
     * @return - объект DTO аэропорта
     */
    DestinationDTO toDTO(Destination destination);

    /**
     * Возвращает объект аэропорта по объекту DTO аэропорта
     * @param destinationDTO - объект DTO аэропорта
     * @return - объект аэропорта
     */
    Destination toEntity(DestinationDTO destinationDTO);
}
