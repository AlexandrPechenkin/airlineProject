package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер менеджера авиакомпании
 */
@Controller
@RequestMapping("/airlineManager")
public class AirlineManagerController {

    /**
     * Возвращает страницу менеджера авикомпании
     * @return - страница менеджера авиакомпании
     */
    @GetMapping
    public String getAirlineManagerPage() {
        return "airlineManager";
    }
}
