package hexlet.code.app.service;

import hexlet.code.app.model.User;
import hexlet.code.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Service for initializing data after application startup.
 */
@Service
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Adds a user after application startup.
     * <p>
     * This method can be overridden to provide custom data initialization logic.
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void addUserAfterStartup() {
        var email = "hexlet@example.com";
        var encodedPassword = userService.hashPassword("qwertyHexlet");

        var user = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        userService.getUserByEmail(email)
                .orElseGet(() -> userRepository.save(user));
    }

}
