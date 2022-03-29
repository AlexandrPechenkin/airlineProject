package app.controllers.airline_manager;

import app.entities.Aircraft;
import app.entities.Flight;
import app.services.interfaces.AircraftService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airline_manager/aircrafts")
@Api(tags = "AirlineManagerAircraftController")
@RequiredArgsConstructor
// Комментарии к коду
public class AirlineManagerAircraftController {
    private final AircraftService aircraftService;

    // Страница Все
    @GetMapping
    public String AllAircraftsPage(Model model) {
        model.addAttribute("aircraftList", aircraftService.getAllAircrafts());
        return "airline_manager/aircrafts/aircrafts";
    }

    // Страница Один
    @GetMapping("/{id}")
    public String AircraftPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("aircraft", (aircraftService.getAircraftById(id)).get());
        return "airline_manager/aircrafts/show_aircraft";
    }

    // Страница Добавить
    @GetMapping("/add")
    public String CreateAircraftPage(Model model) {
        Aircraft aircraft = new Aircraft();
        model.addAttribute("aircraft", aircraft);
        return "airline_manager/aircrafts/add_new_aircraft";
    }

    // Метод Добавить
    @PostMapping
    public String addNewAircraft(@ModelAttribute("aircraft") Aircraft aircraft) {
        aircraftService.createOrUpdateAircraft(aircraft);
        return "redirect:/airline_manager/aircrafts";
    }

    // Страница Обновить
    @GetMapping("/{id}/edit")
    public String UpdateAircraftPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("aircraft", (aircraftService.getAircraftById(id)).get());
        return "airline_manager/aircrafts/update_aircraft";
    }

    // Метод Обновить
    @PutMapping
    public String updateAircraft(@ModelAttribute("flight") Aircraft aircraft) {
        aircraftService.createOrUpdateAircraft(aircraft);
        return "redirect:/airline_manager/aircrafts";
    }

    // Метод Удалить
    @DeleteMapping("/{id}")
    public String deleteAircraft(@PathVariable("id") Long id) {
        aircraftService.deleteAircraftById(id);
        return "redirect:/airline_manager/aircrafts";
    }
}
