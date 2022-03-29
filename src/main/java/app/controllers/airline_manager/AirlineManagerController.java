package app.controllers.airline_manager;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для менеджера авиакомпании
 */
@Controller
@RequestMapping("/airline_manager")
@Api(tags = "AirlineManagerController")
public class AirlineManagerController {

    /**
     * Возвращается страницу менеджера с выбором раздела
     * @return - страница с выбором раздела
     */
    @GetMapping
    public String MainPanelPage() {
        return "airline_manager/main_panel";
    }
}
