package hexlet.code.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.app.model.User;
import hexlet.code.app.model.dto.UserCreateRequest;
import hexlet.code.app.model.dto.UserResponseDTO;
import hexlet.code.app.repository.UserRepository;
import hexlet.code.app.service.UserService;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for UserController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    /**
     * setUp for tests.
     * Creating test User.class.
     */
    @BeforeEach
    public void setUp() {
        String firstUserEmail = faker.internet().emailAddress();

        User user = Instancio.of(User.class)
                .ignore(field(User::getId))
                .set(field(User::getFirstName), "John")
                .set(field(User::getLastName), "Doe")
                .set(field(User::getEmail), firstUserEmail)
                .supply(field(User::getPassword), () -> userService.hashPassword("12345"))
                .create();
        userRepository.save(user);
    }

    /**
     * Testing all CRUD methods.
     * @throws Exception
     */
    @Test
    public void testUserService() throws Exception {
        String secondUserEmail = faker.internet().emailAddress();

        UserCreateRequest userCreateRequest = Instancio.of(UserCreateRequest.class)
                .set(field(UserCreateRequest::getFirstName), "Melinda")
                .set(field(UserCreateRequest::getLastName), "Doe")
                .set(field(UserCreateRequest::getEmail), secondUserEmail)
                .set(field(UserCreateRequest::getPassword), "54321")
                .create();


        var createResponse = mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Melinda"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value(secondUserEmail))
                .andReturn();

        var savedUser = userService.getUserByEmail(secondUserEmail).get();

        var createdUser = mockMvc.perform(get("/api/users/" + savedUser.getId()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserResponseDTO getUserResponse = objectMapper.readValue(createdUser, UserResponseDTO.class);

        assertThat(getUserResponse.getId()).isEqualTo(savedUser.getId());
        assertThat(getUserResponse.getFirstName()).isEqualTo(savedUser.getFirstName());
        assertThat(getUserResponse.getLastName()).isEqualTo(savedUser.getLastName());
        assertThat(getUserResponse.getEmail()).isEqualTo(savedUser.getEmail());

        var userUpdateMap = new HashMap<>();

        userUpdateMap.put("firstName", "Monika");

        mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateMap)))
                .andExpect(status().isOk());

        var updatedUser = userService.getUser(savedUser.getId());
        assertThat(updatedUser.getFirstName()).isEqualTo("Monika");

        mockMvc.perform(delete("/api/users/" + updatedUser.getId()));

        var deletedUser = userService.getUserByEmail(updatedUser.getEmail());
        assertThat(deletedUser).isEmpty();
    }

}
