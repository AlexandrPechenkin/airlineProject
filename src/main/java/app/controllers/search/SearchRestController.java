package app.controllers.search;

import app.entities.route.Route;
import app.services.search.SearchService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "SearchController")
@RequestMapping("/search")
public class SearchRestController {

    private final SearchService searchService;

    public SearchRestController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Получение списка билетов по месту вылета, месту прилета и дате вылета
     */
    @GetMapping("/{from}/{to}/{departureDate}")
    public ResponseEntity<List<Route>> searchTickets(@PathVariable("from") String from, @PathVariable("to") String to,
                                                     @PathVariable("departureDate") LocalDate departureDate) {
        return ResponseEntity.ok(searchService.findTickets(from, to, departureDate));
    }
}
