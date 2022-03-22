package app.entities.mappers.airlineManager;

import app.entities.AirlineManager;
import app.entities.dtos.AirlineManagerDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов AirlineManager и AirlineManagerDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AirlineManagerMapper {

    /**
     * Приведение AirlineManager к AirlineManagerDTO.
     *
     * @param airlineManager - данные, которые передаются с бэкенда.
     * @return {@link AirlineManagerDTO}
     */
    AirlineManagerDTO toDto(AirlineManager airlineManager);

    /**
     * Приведение AirlineManagerDTO к AirlineManager.
     *
     * @param airlineManagerDTO - данные, которые передаются с фронтенда.
     * @return {@link AirlineManagerDTO}
     */
    AirlineManager toEntity(AirlineManagerDTO airlineManagerDTO);
}
