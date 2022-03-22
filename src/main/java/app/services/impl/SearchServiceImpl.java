package app.services.impl;

import app.entities.Search;
import app.repositories.SearchRepository;
import app.services.interfaces.SearchService;
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