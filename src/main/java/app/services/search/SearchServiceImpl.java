package app.services.search;

import app.repositories.search.SearchRepository;
import org.springframework.stereotype.Service;


@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }


}
