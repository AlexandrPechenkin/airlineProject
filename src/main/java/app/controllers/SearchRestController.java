package app.controllers;

import app.entities.Ticket;
import app.services.TicketService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "SearchController")
@RequestMapping("/search")
public class SearchRestController {

    private final TicketService ticketService;

    public SearchRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    //получение списка всех билетов
    @GetMapping("/all")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    //получение списка всех билетов по месту вылета
    @GetMapping("/origin/{origin}")
    public ResponseEntity<List<Ticket>> searchTicketOrigin(@PathVariable("origin") String origin) {
        return ResponseEntity.ok(ticketService.findTicketsByOrigin(origin));
    }


    //получение списка всех билетов по месту прилета
    @GetMapping("/destination/{destination}")
    public ResponseEntity<List<Ticket>> searchTicketDestination(@PathVariable("destination") String destination) {
        return ResponseEntity.ok(ticketService.findTicketsByDestination(destination));
    }

    //получение списка всех билетов по месту вылета и месту прилета
    @GetMapping("/{origin}/{destination}")
    public ResponseEntity<List<Ticket>> searchTickets(@PathVariable("origin") String origin,
                                                      @PathVariable("destination") String destination) {
        return ResponseEntity.ok(ticketService.findTicketsByOriginAndDestination(origin, destination));
    }

}
