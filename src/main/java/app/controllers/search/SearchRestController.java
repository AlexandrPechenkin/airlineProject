package app.controllers.search;

import app.services.search.SearchService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "SearchController")
@RequestMapping("/search")
public class SearchRestController {

    private final SearchService searchService;

    public SearchRestController(SearchService searchService) {
        this.searchService = searchService;
    }

}
