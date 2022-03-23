package app.controllers;

import app.entities.Flight;
import app.entities.Ticket;
import app.services.interfaces.FlightService;
import app.services.interfaces.TicketService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/airline_manager")
@Api(tags = "AirlineManagerController")
@AllArgsConstructor
public class AirlineManagerController {
    private final FlightService flightService;
    private final TicketService ticketService;

    @GetMapping("/flights")
    public String showAllFlights(Model model) {
        List<Flight> flightList = flightService.getAllFlights();
        model.addAttribute(flightList);
        return "airline_manager/flights/flights";
    }

    @GetMapping("/flights/add")
    public String addNewFlightPage(Model model) {
        Flight flight = new Flight();
        model.addAttribute("flight", flight);
        return "airline_manager/flights/add_new_flight";
    }

    @PostMapping("/flights")
    public String addNewFlight(@ModelAttribute("flight") @Valid Flight flight) {
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
