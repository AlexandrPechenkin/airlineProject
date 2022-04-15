package app.controllers;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = "AdminController")
@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String getPage() {
        return "adminPanel";
    }
}
