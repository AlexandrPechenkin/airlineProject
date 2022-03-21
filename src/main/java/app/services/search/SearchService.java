package app.services.search;


import app.entities.search.Search;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    List<Search> getAll();
    Optional<Search> getById(Long Id);
}
