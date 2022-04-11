package app.entities.mappers.searchResult;

import app.entities.SearchResult;
import app.entities.dtos.SearchResultDTO;
import app.entities.mappers.flight.FlightListMapper;
import app.entities.mappers.flight.FlightMapper;
import app.entities.mappers.flightContainer.FlightContainerMapper;
import app.entities.mappers.flightListWrapper.FlightListWrapperMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {FlightContainerMapper.class, FlightListMapper.class, FlightMapper.class, FlightListWrapperMapper.class})
public interface SearchResultMapper {
    FlightListMapper MAPPER = Mappers.getMapper(FlightListMapper.class);
    SearchResultDTO toDto(SearchResult searchResult);
    SearchResult toEntity(SearchResultDTO searchResultDTO);
}
