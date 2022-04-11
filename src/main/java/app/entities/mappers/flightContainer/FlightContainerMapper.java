package app.entities.mappers.flightContainer;

import app.entities.FlightContainer;
import app.entities.dtos.FlightContainerDTO;
import app.entities.mappers.flight.FlightListMapper;
import app.entities.mappers.flight.FlightMapper;
import app.entities.mappers.flightListWrapper.FlightListWrapperMapper;
import app.entities.mappers.searchResult.SearchResultMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SearchResultMapper.class, FlightListMapper.class, FlightMapper.class, FlightListWrapperMapper.class})
public interface FlightContainerMapper {
    FlightContainerDTO toDTO(FlightContainer flightContainer);
    FlightContainer toEntity(FlightContainerDTO flightContainerDTO);
}
