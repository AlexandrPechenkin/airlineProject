package app.mappers.destination;

import app.entities.destination.Destination;
import app.entities.destination.dto.DestinationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

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
