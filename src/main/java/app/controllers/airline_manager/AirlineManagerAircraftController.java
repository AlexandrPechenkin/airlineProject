package app.controllers.airline_manager;

import app.entities.Aircraft;
import app.entities.Flight;
import app.services.interfaces.AircraftService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Контроллер для раздела воздушных судов менеджера авиакомпании
 */
@Controller
@RequestMapping("/airline_manager/aircrafts")
@Api(tags = "AirlineManagerAircraftController")
@RequiredArgsConstructor
public class AirlineManagerAircraftController {
    private final AircraftService aircraftService;

    /**
     * Возвращает страницу с информацией о всех воздушных судах
     * Можно добавить новое судно или перейти на страницу конкретного судна
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница со всеми воздушными судами
     */
    @GetMapping
    public String AllAircraftsPage(Model model) {
        model.addAttribute("aircraftList", aircraftService.getAllAircrafts());
        return "airline_manager/aircrafts/aircrafts";
    }

    /**
     * Возвращает страницу с выбранным из общего списка воздушным судном.
     * Можно изменить информацию о судне или удалить судно
     * @param id - ID судна
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница с информацией о конкретном судне
     */
    @GetMapping("/{id}")
    public String AircraftPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("aircraft", (aircraftService.getAircraftById(id)).get());
        return "airline_manager/aircrafts/show_aircraft";
    }

    /**
     * Возвращает страницу с формой добавления нового воздушного судна
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница добавления нового судна
     */
    @GetMapping("/add")
    public String CreateAircraftPage(Model model) {
        model.addAttribute("aircraft", new Aircraft());
        return "airline_manager/aircrafts/add_new_aircraft";
    }

    /**
     * Возвращает страницу с изменением данных конкретного воздушного судна
     * @param id - ID судна
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница с формой изменения данных конкретного судна
     */
    @GetMapping("/{id}/edit")
    public String UpdateAircraftPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("aircraft", (aircraftService.getAircraftById(id)).get());
        return "airline_manager/aircrafts/update_aircraft";
    }

    /**
     * Сохраняет новое воздушное судно в БД, возвращает на страницу со всеми судами
     * Если заполненные поля не соответствуют требованиям, возвращает на страницу добавления нового судна
     * @param aircraft - судно
     * @param bindingResult - объект BindingResult для проверки данных
     * @return - редирект на страницу со всеми судами
     */
    @PostMapping
    public String addNewAircraft(@ModelAttribute("aircraft") @Valid Aircraft aircraft, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "airline_manager/aircrafts/add_new_aircraft";
        }
        aircraftService.createOrUpdateAircraft(aircraft);
        return "redirect:/airline_manager/aircrafts";
    }

    /**
     * Обновляет воздушное судно в БД, возвращает на страницу со всеми судами
     * Если заполненные поля не соответствуют требованиям, возвращает на страницу с изменением данных
     * конкретного воздушного судна
     * @param aircraft - судно
     * @param bindingResult - объект BindingResult для проверки данных
     * @return - редирект на страницу со всеми судами
     */
    @PutMapping
    public String updateAircraft(@ModelAttribute("flight") @Valid Aircraft aircraft, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "airline_manager/aircrafts/update_aircraft";
        }
        aircraftService.createOrUpdateAircraft(aircraft);
        return "redirect:/airline_manager/aircrafts";
    }

    /**
     * Удаляет воздушное судно из БД, возвращает на страницу со всеми судами
     * @param id - ID судна
     * @return - редирект на страницу со всеми судами
     */
    @DeleteMapping("/{id}")
    public String deleteAircraft(@PathVariable("id") Long id) {
        aircraftService.deleteAircraftById(id);
        return "redirect:/airline_manager/aircrafts";
    }
}
