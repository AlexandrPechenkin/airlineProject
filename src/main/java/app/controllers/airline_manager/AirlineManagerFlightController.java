package app.controllers.airline_manager;

import app.entities.Flight;
import app.services.interfaces.FlightService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airline_manager/flights")
@Api(tags = "AirlineManagerFlightController")
@RequiredArgsConstructor
// Валидация
// Комментарии к коду
public class AirlineManagerFlightController {
    private final FlightService flightService;

    // Страница Все
    @GetMapping
    public String AllFlightsPage(Model model) {
        model.addAttribute("flightList", flightService.getAllFlights());
        return "airline_manager/flights/flights";
    }

    // Страница Один
    @GetMapping("/{id}")
    public String FlightPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", (flightService.findById(id)).get());
        return "airline_manager/flights/show_flight";
    }

    // Страница Добавить
    @GetMapping("/add")
    public String CreateFlightPage(Model model) {
        Flight flight = new Flight();
        model.addAttribute("flight", flight);
        return "airline_manager/flights/add_new_flight";
    }

    // Метод Добавить
    @PostMapping
    public String addNewFlight(@ModelAttribute("flight") Flight flight) {
        flightService.createOrUpdateFlight(flight);
        return "redirect:/airline_manager/flights";
    }

    // Страница Обновить
    @GetMapping("/{id}/edit")
    public String UpdateFlightPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", (flightService.findById(id)).get());
        return "airline_manager/flights/update_flight";
    }

    // Метод Обновить
    @PutMapping
    public String updateFlight(@ModelAttribute("flight") Flight flight) {
        flightService.createOrUpdateFlight(flight);
        return "redirect:/airline_manager/flights";
    }

    // Метод Удалить
    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable("id") Long id) {
        flightService.removeFlight((flightService.findById(id)).get());
        return "redirect:/airline_manager/flights";
    }
}
