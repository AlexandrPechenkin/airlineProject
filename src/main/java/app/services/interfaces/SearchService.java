package app.services.interfaces;


import app.entities.Search;
import app.entities.SearchResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SearchService {
    List<Search> getAll();
    Optional<Search> getById(Long Id);
    SearchResult getSearchResultByCitiesAndLocalDates(String cityFrom, String cityTo,
                                                      LocalDate departureDate, LocalDate returnDate);
}
