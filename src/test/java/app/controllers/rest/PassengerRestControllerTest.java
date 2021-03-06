package app.controllers.rest;

import app.AirlineApplication;
import app.entities.Passenger;
import app.entities.Passport;
import app.entities.Role;
import app.entities.mappers.passenger.PassengerMapper;
import app.services.interfaces.PassengerService;
import app.services.interfaces.RoleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AirlineApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
@ActiveProfiles("integrationtest")
@FieldDefaults(level = AccessLevel.PRIVATE)
@WithMockUser(username = "admin@mai.ru", password = "123", authorities = "ADMIN")
class PassengerRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired @Qualifier("passengerServiceImpl")
    PassengerService passengerService;

    @Autowired
    PassengerMapper passengerMapper;

    @Autowired
    RoleService roleService;

    static final String api = "/api/passenger";

    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Passenger createPassenger() {
        return Passenger.builder()
                .firstName("Dereck")
                .lastName("Storm")
                .middleName("Totoro")
                .dateOfBirth(LocalDate.of(1992, 2, 15))
                .email("Airlines@test.com")
                .password("123")
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

        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(passengerMapper.toDto(passenger)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(passenger.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(passenger.getLastName())));
    }

    @Test
    void whenCreatePassengerWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenPassengerExist_whenUpdatePassenger_thenStatus200() throws Exception {
        Passenger passenger = passengerService.createOrUpdatePassenger(createPassenger());
        passenger.setFirstName("Lol");
        passenger.setLastName("Loooool");
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(passenger))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(passenger.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(passenger.getLastName())));
    }

    @Test
    void whenUpdatePassengerWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenPassengerExist_whenGetByIdPassenger_thenStatus200() throws Exception {
        Passenger passenger = passengerService.createOrUpdatePassenger(createPassenger());

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(passenger))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenPassengerExist_whenGetWithoutIdPassenger_thenStatus404() throws Exception {
        Passenger passenger = passengerService.createOrUpdatePassenger(createPassenger());

        mvc.perform(get(api + "/{id}", 228)
                        .content(objectMapper.writeValueAsString(passenger))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}