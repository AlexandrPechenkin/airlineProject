package app.controllers;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/airline_manager")
@Api(tags = "AirlineManagerController")
public class AirlineManagerController {

    @GetMapping
    public String MainPanelPage() {
        return "airline_manager/main_panel";
    }
}
