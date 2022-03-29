package app.controllers.airline_manager;

import app.entities.Flight;
import app.services.interfaces.FlightService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Контроллер для раздела рейсов менеджера авиакомпании
 */
@Controller
@RequestMapping("/airline_manager/flights")
@Api(tags = "AirlineManagerFlightController")
@RequiredArgsConstructor
public class AirlineManagerFlightController {
    private final FlightService flightService;

    /**
     * Возвращает страницу с информацией о всех рейсах и возможностьью фильтрации рейсов по дате вылета
     * Можно добавить новый рейс или перейти на страницу конкретного рейса
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница со всеми рейсами
     */
    @GetMapping
    public String AllFlightsPage(Model model) {
        model.addAttribute("flightList", flightService.getAllFlights());
        return "airline_manager/flights/flights";
    }

    /**
     * Возвращает страницу с выбранным из общего списка рейсом. Можно изменить информацию о рейсе или удалить рейс
     * @param id - ID рейса
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница с информацией о конкретном рейсе
     */
    @GetMapping("/{id}")
    public String FlightPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", (flightService.findById(id)).get());
        return "airline_manager/flights/show_flight";
    }

    /**
     * Возвращает страницу с формой добавления нового рейса
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница добавления нового рейса
     */
    @GetMapping("/add")
    public String CreateFlightPage(Model model) {
        model.addAttribute("flight", new Flight());
        return "airline_manager/flights/add_new_flight";
    }

    /**
     * Возвращает страницу с изменением данных конкретного рейса
     * @param id - ID рейса
     * @param model - объект класса Model, предназначенный для добавления атрибутов в модель
     * @return - страница с формой изменения данных конкретного рейса
     */
    @GetMapping("/{id}/edit")
    public String UpdateFlightPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("flight", (flightService.findById(id)).get());
        return "airline_manager/flights/update_flight";
    }

    /**
     * Сохраняет новый рейс в БД, возвращает на страницу со всеми рейсами
     * Если заполненные поля не соответствуют требованиям, возвращает на страницу добавления нового рейса
     * @param flight - новый рейс
     * @param bindingResult - объект BindingResult для проверки данных
     * @return
     */
    @PostMapping
    public String addNewFlight(@ModelAttribute("flight") @Valid Flight flight, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "airline_manager/flights/add_new_flight";
        }
        flightService.createOrUpdateFlight(flight);
        return "redirect:/airline_manager/flights";
    }

    /**
     * Обновляет рейс в БД, возвращает на страницу со всеми рейсами
     * Если заполненные поля не соответствуют требованиям, возвращает на страницу с изменением данных конкретного рейса
     * @param flight - рейс
     * @param bindingResult - объект BindingResult для проверки данных
     * @return - редирект на страницу со всеми рейсами
     */
    @PutMapping
    public String updateFlight(@ModelAttribute("flight") @Valid Flight flight, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "airline_manager/flights/update_flight";
        }
        flightService.createOrUpdateFlight(flight);
        return "redirect:/airline_manager/flights";
    }

    /**
     * Удаляет рейс из БД, возвращает на страницу со всеми рейсами
     * @param id - ID рейса
     * @return - редирект на страницу со всеми рейсами
     */
    @DeleteMapping("/{id}")
    public String deleteFlight(@PathVariable("id") Long id) {
        flightService.removeFlight((flightService.findById(id)).get());
        return "redirect:/airline_manager/flights";
    }
}
