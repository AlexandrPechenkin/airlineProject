package app.services.interfaces;


import app.entities.Search;

import java.util.List;
import java.util.Optional;

public interface SearchService {
    List<Search> getAll();
    Optional<Search> getById(Long Id);
}
