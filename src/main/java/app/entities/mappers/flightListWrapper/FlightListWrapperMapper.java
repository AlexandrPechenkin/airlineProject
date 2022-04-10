package app.entities.mappers.flightListWrapper;

import app.entities.FlightListWrapper;
import app.entities.dtos.FlightListWrapperDTO;
import app.entities.mappers.flight.FlightListMapper;
import app.entities.mappers.flight.FlightMapper;
import app.entities.mappers.flightContainer.FlightContainerMapper;
import app.entities.mappers.searchResult.SearchResultMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SearchResultMapper.class,FlightContainerMapper.class,FlightListMapper.class,
                FlightMapper.class,FlightListWrapperMapper.class})
public interface FlightListWrapperMapper {
    FlightListWrapperDTO toDTO(FlightListWrapper flightListWrapper);
    FlightListWrapper toEntity(FlightListWrapperDTO flightListWrapperDTO);
}
