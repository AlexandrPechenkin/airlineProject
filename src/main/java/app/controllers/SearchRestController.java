package app.controllers;

import app.entities.ticket.Ticket;
import app.services.search.SearchService;
import app.services.ticket.TicketService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "SearchController")
@RequestMapping("/search")
public class SearchRestController {

    private final TicketService ticketService;
    private final SearchService searchService;

    public SearchRestController(TicketService ticketService, SearchService searchService) {
        this.ticketService = ticketService;
        this.searchService = searchService;
    }

    /**
     * получение списка всех билетов
     */
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    /**
     * Получение списка билетов по месту вылета, месту прилета и дате вылета
     */
    @GetMapping("/{route}/{departureDate}")
    public ResponseEntity<List<Ticket>> searchTickets(@PathVariable("route") String route,
                                                      @PathVariable("departureDate") LocalDate departureDate) {
        return ResponseEntity.ok(searchService.findTickets(route, departureDate));
    }
}
