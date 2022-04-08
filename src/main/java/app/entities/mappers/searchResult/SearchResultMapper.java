package app.entities.mappers.searchResult;

import app.entities.DestinationResource;
import app.entities.Flight;
import app.entities.SearchResult;
import app.entities.dtos.FlightDTO;
import app.entities.dtos.SearchResultDTO;
import app.entities.mappers.flight.FlightListMapper;
import app.entities.mappers.flight.FlightMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {FlightListMapper.class, FlightMapper.class})
public interface SearchResultMapper {
    FlightListMapper MAPPER = Mappers.getMapper(FlightListMapper.class);
    SearchResultDTO toDto(SearchResult searchResult);
    SearchResult toEntity(SearchResultDTO searchResultDTO);

    default Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> flightsListToFlightDTOsList(
            Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> map) {
        Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> dtoFlightsList = new HashMap<>();
        map.forEach((numberOfStops, multiMap) -> {
            List<List<FlightDTO>> dtoFlights = new ArrayList<>();
            multiMap.forEach((res, listOfLists) -> {
                listOfLists.forEach(list -> {
                    dtoFlights.add(MAPPER.toDTOList(list));
                });
                Map<DestinationResource, List<List<FlightDTO>>> temp = new HashMap<>();
                temp.put(res, dtoFlights);
                MultiValueMap<DestinationResource, List<FlightDTO>> multiValueMapFLightList =
                        new MultiValueMapAdapter<>(temp);
                dtoFlightsList.put(numberOfStops, multiValueMapFLightList);
            });
        });
        return dtoFlightsList;
    }

    default Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> flightsDTOListToFlightsList(
            Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> map) {
        Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> flightsList = new HashMap<>();
        map.forEach((numberOfStops, multiMap) -> {
            List<List<Flight>> dtoFlights = new ArrayList<>();
            multiMap.forEach((res, listOfLists) -> {
                listOfLists.forEach(list -> {
                    dtoFlights.add(MAPPER.toEntityList(list));
                });
                Map<DestinationResource, List<List<Flight>>> temp = new HashMap<>();
                temp.put(res, dtoFlights);
                MultiValueMap<DestinationResource, List<Flight>> multiValueMapFLightList =
                        new MultiValueMapAdapter<>(temp);
                flightsList.put(numberOfStops, multiValueMapFLightList);
            });
        });
        return flightsList;
    }
}
