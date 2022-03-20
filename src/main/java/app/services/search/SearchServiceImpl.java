package app.services.search;

import app.repositories.search.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;



}
