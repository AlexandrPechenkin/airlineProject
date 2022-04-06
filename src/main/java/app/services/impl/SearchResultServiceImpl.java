package app.services.impl;

import app.entities.SearchResult;
import app.repositories.SearchResultRepository;
import app.services.interfaces.DestinationService;
import app.services.interfaces.SearchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchResultServiceImpl implements SearchResultService {
    SearchResultRepository searchResultRepository;
    DestinationService destinationService;

    public SearchResultServiceImpl(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Override
    public SearchResult createOrUpdateSearchResult(SearchResult searchResult) {
        return searchResultRepository.save(searchResult);
    }

    @Override
    public Optional<SearchResult> findById(long id) {
        return searchResultRepository.findById(id);
    }

    @Override
    public List<SearchResult> findAll() {
        return searchResultRepository.findAll();
    }

    @Override
    public void deleteSearchResultById(long id) {
        searchResultRepository.deleteById(id);
    }
}
