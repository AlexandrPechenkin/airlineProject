package app.services.impl;

import app.entities.SearchResult;
import app.repositories.SearchResultRepository;
import app.services.interfaces.DestinationService;
import app.services.interfaces.SearchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SearchResultServiceImpl implements SearchResultService {
    @Autowired @Qualifier("searchResultRepository")
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
