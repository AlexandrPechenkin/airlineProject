package app.services.interfaces;


import app.entities.*;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SearchService {
    List<Search> getAll();
    Optional<Search> getById(Long Id);
    SearchResult createSearchResult(String cityFrom, String cityTo, LocalDate departureDate);
    Map<Integer, MultiValueMap<DestinationResource, List<Route>>> getRoutes(List<DestinationResource> resourceFrom,
                                                                            List<DestinationResource> resourceTo,
                                                                            LocalDate departureDate);
    Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> getFlights(
            Map<Integer, MultiValueMap<DestinationResource, List<Route>>> resourceFLight,
            LocalDate departureDate);
}
