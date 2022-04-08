package app.services.interfaces;

import app.entities.SearchResult;

import java.util.List;
import java.util.Optional;

public interface SearchResultService {
    SearchResult createOrUpdateSearchResult(SearchResult searchResult);
    Optional<SearchResult> findById(long id);
    List<SearchResult> findAll();
    void deleteSearchResultById(long id);
}
