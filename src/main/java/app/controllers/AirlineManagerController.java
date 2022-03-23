package app.controllers;

import app.entities.Flight;
import app.services.interfaces.FlightService;
import app.services.interfaces.TicketService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/airline_manager")
@Api(tags = "AirlineManagerController")
@AllArgsConstructor
public class AirlineManagerController {
    private final FlightService flightService;
    private final TicketService ticketService;

//    // Страница Один
//    @GetMapping("/flights/{id}")
//    public String showFlightById(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("flight", flightService.findById(id));
//        return null;
//    }

    // Страница Все
    @GetMapping("/flights")
    public String showAllFlights(Model model) {
        List<Flight> flightList = flightService.getAllFlights();
        model.addAttribute(flightList);
        return "airline_manager/flights/flights";
    }

    // Страница Добавить
    @GetMapping("/flights/add")
    public String addNewFlightPage(Model model) {
        Flight flight = new Flight();
        model.addAttribute("flight", flight);
        return "airline_manager/flights/add_new_flight";
    }

    // Страница Обновить
    @GetMapping("/flights/update/{id}")
    public String updateFlightPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", flightService.findById(id));
        return "airline_manager/flights/update_flight";
    }

    // Метод Добавить
    @PostMapping("/flights")
    public String addNewFlight(@ModelAttribute("flight") Flight flight) {
        flightService.createOrUpdateFlight(flight);
        return "redirect:/airline_manager/flights";
    }

    // Метод Обновить
    @PutMapping("/flights")
    public String updateFlight(@ModelAttribute("flight") Flight flight) {
        flightService.createOrUpdateFlight(flight);
        return "redirect:/airline_manager/flights";
    }

//    @GetMapping("/tickets")
//    public String showAllTickets(Model model) {
//        List<Ticket> ticketList = ticketService.getAllTickets();
//        model.addAttribute(ticketList);
//        return "airlineManager/tickets/tickets";
//    }
}
