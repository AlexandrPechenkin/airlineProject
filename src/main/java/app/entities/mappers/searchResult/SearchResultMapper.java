package app.entities.mappers.searchResult;

import app.entities.SearchResult;
import app.entities.dtos.SearchResultDTO;
import app.entities.mappers.flight.FlightMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = FlightMapper.class)
public interface SearchResultMapper {
    SearchResultDTO toDto(SearchResult searchResult);
    SearchResult toEntity(SearchResultDTO searchResultDTO);
}
