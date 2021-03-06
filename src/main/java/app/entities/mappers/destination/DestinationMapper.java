package app.entities.mappers.destination;

import app.entities.Destination;
import app.entities.dtos.DestinationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Маппер для аэропорта
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DestinationMapper {

    /**
     * Возвращает DTO аэропорта по аэропорту
     * @param destination - аэропорт
     * @return - DTO аэропорта
     */
    DestinationDTO toDTO(Destination destination);

    /**
     * Возвращает аэропорт по DTO аэропорта
     * @param destinationDTO - DTO аэропорта
     * @return - аэропорт
     */
    Destination toEntity(DestinationDTO destinationDTO);
}
