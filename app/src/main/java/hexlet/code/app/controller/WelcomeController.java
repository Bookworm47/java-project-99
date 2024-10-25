package hexlet.code.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for welcome messages.
 */
@RestController
@RequestMapping("/")
public class WelcomeController {

    /**
     * Returns a welcome message.
     * <p>
     * This method can be overridden to provide custom welcome messages.
     *
     * @return a welcome message string
     */
    @GetMapping("/welcome")
    public String welcomeToSpring() {
        return "Welcome to Spring";
    }

}
