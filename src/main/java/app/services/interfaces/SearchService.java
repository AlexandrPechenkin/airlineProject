package app.services.interfaces;


import app.entities.DestinationResource;
import app.entities.Route;
import app.entities.Search;
import app.entities.SearchResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SearchService {
    List<Search> getAll();
    Optional<Search> getById(Long Id);
    SearchResult createSearchResult(String cityFrom, String cityTo, LocalDate departureDate);
    Map<Integer, Map<DestinationResource, List<List<Route>>>> getRoutes(List<DestinationResource> resourceFrom,
                                                                        List<DestinationResource> resourceTo,
                                                                        LocalDate localDate);
}
