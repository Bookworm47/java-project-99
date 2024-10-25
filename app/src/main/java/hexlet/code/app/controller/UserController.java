package hexlet.code.app.controller;

import hexlet.code.app.model.dto.UserCreateRequest;
import hexlet.code.app.model.dto.UserResponseDTO;
import hexlet.code.app.model.dto.UserUpdateRequest;
import hexlet.code.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing users.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a list of users.
     * <p>
     * This method can be overridden to provide custom user retrieval logic.
     *
     * @return a list of UserResponseDTO instances
     */
    @GetMapping
    public List<UserResponseDTO> getUsers() {
        return userService.getUsers();
    }

    /**
     * Retrieves a user by ID.
     * <p>
     * This method can be overridden to provide custom user retrieval logic.
     *
     * @param id the ID of the user
     * @return a UserResponseDTO instance
     */
    @GetMapping("{id}")
    public UserResponseDTO getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    /**
     * Creates a new user.
     * <p>
     * This method can be overridden to provide custom user creation logic.
     *
     * @param user the UserCreateRequest instance
     * @return a UserResponseDTO instance
     */
    @PostMapping
    public UserResponseDTO createUser(@RequestBody UserCreateRequest user) {
        return userService.createUser(user);
    }

    /**
     * Updates an existing user.
     * <p>
     * This method can be overridden to provide custom user update logic.
     *
     * @param userUpdateRequest the UserUpdateRequest instance
     * @param id the ID of the user
     * @return a UserResponseDTO instance
     */
    @PutMapping("{id}")
    public UserResponseDTO updateUser(@RequestBody UserUpdateRequest userUpdateRequest,
                                      @PathVariable("id") Long id) {
        return userService.updateUser(userUpdateRequest, id);
    }

    /**
     * Deletes a user by ID.
     * <p>
     * This method can be overridden to provide custom user deletion logic.
     *
     * @param id the ID of the user
     */
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
