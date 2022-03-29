package app.controllers.airline_manager;

import app.services.interfaces.TicketService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/airline_manager/tickets")
@Api(tags = "AirlineManagerTicketController")
@RequiredArgsConstructor
// Комментарии к коду
public class AirlineManagerTicketController {
    private final TicketService ticketService;

    // Страница Все
    @GetMapping
    public String AllTicketsPage(Model model) {
        model.addAttribute("ticketList", ticketService.getAllTickets());
        return "airline_manager/tickets/tickets";
    }
}
