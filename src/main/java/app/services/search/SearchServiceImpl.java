package app.services.search;

import app.entities.search.Search;
import app.repositories.search.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;

    @Override
    public List<Search> getAll() {
        return searchRepository.findAll();
    }

    @Override
    public Optional<Search> getById(Long id) { return searchRepository.findById(id); }


}
