package app.entities.mappers.route;

import app.entities.Route;
import app.entities.dtos.RouteDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {RouteMapper.class})
public interface RouteMapper {
    /**
     * Метод сопоставляет Flight c FlightDTO
     *
     * @param route - перелет
     */
    RouteDTO toDTO(Route route);

    /**
     * Метод сопоставляет FlightDTO c Flight
     *
     * @param routeDTO - Поля перелета, которые отдаются наружу
     */
    Route toEntity(RouteDTO routeDTO);

}