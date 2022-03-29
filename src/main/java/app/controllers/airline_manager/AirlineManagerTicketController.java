package app.controllers.airline_manager;

import app.services.interfaces.TicketService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для раздела билетов менеджера авиакомпании
 */
@Controller
@RequestMapping("/airline_manager/tickets")
@Api(tags = "AirlineManagerTicketController")
@RequiredArgsConstructor
public class AirlineManagerTicketController {
    private final TicketService ticketService;

    /**
     * Возвращает страницу с информацией о всех билетах и возможностьью фильтрации билетов по дате вылета
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница со всеми билетами
     */
    @GetMapping
    public String AllTicketsPage(Model model) {
        model.addAttribute("ticketList", ticketService.getAllTickets());
        return "airline_manager/tickets/tickets";
    }
}
