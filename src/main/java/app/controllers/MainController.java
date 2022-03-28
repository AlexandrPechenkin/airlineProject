package app.controllers;

import app.services.interfaces.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Api(tags = "MainController")
public class MainController {
    private final UserService userService;

    @GetMapping("/")
    public ModelAndView index(Model model, Principal principal) {
        if (Objects.nonNull(principal)) {
            model.addAttribute("userInfo", userService.findByEmail(principal.getName()));
        }
        return new ModelAndView("index");
    }
}
