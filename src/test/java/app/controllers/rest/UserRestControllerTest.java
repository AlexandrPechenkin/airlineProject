package app.controllers.rest;

import app.AirlineApplication;
import app.entities.Admin;
import app.entities.Passenger;
import app.entities.Passport;
import app.entities.Role;
import app.services.interfaces.RoleService;
import app.services.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AirlineApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
@ActiveProfiles("integrationtest")
@FieldDefaults(level = AccessLevel.PRIVATE)
@WithMockUser(username = "admin@mai.ru", password = "123", authorities = "ADMIN")
public class UserRestControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired @Qualifier("userServiceImpl")
    UserService userService;

    @Autowired
    RoleService roleService;

    final String api = "/api/user";
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Admin createAdmin() {
        return Admin.builder().email("admin_user@mail.com")
                .password("password_admin_user")
                .nickname("nickname_admin_user")
                .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
                .build();
    }

    Passenger createPassenger() {
        return Passenger.builder()
                .firstName("Dereck")
                .lastName("Storm")
                .middleName("Totoro")
                .dateOfBirth(LocalDate.of(1992, 2, 15))
                .email("Airlines@test.com")
                .password("password_airlines")
                .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
                .passport(
                        Passport.builder()
                                .firstName("Dereck")
                                .lastName("Storm")
                                .middleName("Totoro")
                                .dateOfBirth(LocalDate.of(1990, 2, 15))
                                .gender("Male")
                                .birthplace("US")
                                .residenceRegistration("New York")
                                .seriesAndNumber("1234 567890")
                                .build()
                )
                .build();
    }



    @Test
    void whenCreatePassenger_thenStatus201() throws Exception {
        Passenger passenger = createPassenger();
        mvc.perform(post(api+"/passenger")
                    .content(objectMapper.writeValueAsString(passenger))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(passenger.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(passenger.getLastName())));
    }

    @Test
    void whenCreateAdmin_thenStatus201() throws Exception {
        Admin admin = createAdmin();
        mvc.perform(post(api + "/admin")
                        .content(objectMapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nickname", is(admin.getNickname())))
                .andExpect(jsonPath("$.email", is(admin.getEmail())));
    }
}
