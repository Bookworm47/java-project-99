package hexlet.code.app.service;

import hexlet.code.app.mapper.UserMapper;
import hexlet.code.app.model.User;
import hexlet.code.app.model.dto.UserCreateRequest;
import hexlet.code.app.model.dto.UserResponseDTO;
import hexlet.code.app.model.dto.UserUpdateRequest;
import hexlet.code.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing users.
 */
@Validated
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieves a user by ID.
     * <p>
     * This method can be overridden to provide custom user retrieval logic.
     *
     * @param id the ID of the user
     * @return a UserResponseDTO instance
     */
    public UserResponseDTO getUser(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователя с id %d е найдено", id)));

        return userMapper.mapUserToUserResponse(user);
    }

    /**
     * Retrieves a list of users.
     * <p>
     * This method can be overridden to provide custom user retrieval logic.
     *
     * @return a list of UserResponseDTO instances
     */
    public List<UserResponseDTO> getUsers() {
        var users = userRepository.findAll();

        return userMapper.mapUsersToResponseList(users);
    }

    /**
     * Creates a new user.
     * <p>
     * This method can be overridden to provide custom user creation logic.
     *
     * @param userCreateRequest the UserCreateRequest instance
     * @return a UserResponseDTO instance
     */
    @Transactional
    public UserResponseDTO createUser(@Valid UserCreateRequest userCreateRequest) {
        var user = userMapper.mapUserRequestToUser(userCreateRequest);
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);

        return userMapper.mapUserToUserResponse(user);
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
    @Transactional
    public UserResponseDTO updateUser(@Valid UserUpdateRequest userUpdateRequest, Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Пользователя с id %d не найдено", id)));
        userMapper.updateUser(userUpdateRequest, user);

        if (userUpdateRequest.getPassword() != null && userUpdateRequest.getPassword().isPresent()) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        userRepository.save(user);

        return userMapper.mapUserToUserResponse(user);
    }

    /**
     * Deletes a user by ID.
     * <p>
     * This method can be overridden to provide custom user deletion logic.
     *
     * @param id the ID of the user
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteUserById(id);
    }

    /**
     * Retrieves a user by email.
     * <p>
     * This method can be overridden to provide custom user retrieval logic.
     *
     * @param email the email of the user
     * @return an Optional containing the User instance, if found
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Hashes a password.
     * <p>
     * This method can be overridden to provide custom password hashing logic.
     *
     * @param notHashedPassword the plain text password
     * @return the hashed password
     */
    public String hashPassword(String notHashedPassword) {
        return encoder.encode(notHashedPassword);
    }

}
