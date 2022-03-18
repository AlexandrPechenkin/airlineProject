package app.airlineManager;

import app.AirlineApplication;

import app.entities.AirlineManager;
import app.services.interfaces.AirlineManagerService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AirlineApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
@ActiveProfiles("integrationtest")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AirlineManagerRestControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired @Qualifier("airlineManagerServiceImpl")
    AirlineManagerService airlineManagerService;

    final String api = "/airlineManager";
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    AirlineManager createAirlineManager() {
        return AirlineManager.builder()
                .email("airline_manager@mail.com")
                .roles("airline_manager")
                .password("airline")
                .parkName("airline_manager_park_name")
                .build();
    }

    @Test
    void whenCreateAirlineManager_thenStatus201() throws Exception {
        AirlineManager airlineManager = createAirlineManager();
        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(airlineManager.getEmail())))
                .andExpect(jsonPath("$.password", is(airlineManager.getPassword())))
                .andExpect(jsonPath("$.parkName", is(airlineManager.getParkName())));
        airlineManagerService.deleteAirlineManager(airlineManager);
    }

    @Test
    void whenCreateAirlineManagerWithEmptyBody_thenStatus404() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAirlineManagerExist_whenGetByIdExistedAirlineManager_thenStatus200() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(get(api + "/{id}", airlineManager.getId())
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(airlineManager.getId().intValue())));
        airlineManagerService.deleteAirlineManager(airlineManager);
    }

    @Test
    void givenAirlineManagerExist_whenGetByIdNotExistedAirlineManager_thenStatus404() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(get(api + "/{id}", 123123)
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        airlineManagerService.deleteAirlineManager(airlineManager);
    }

    @Test
    void givenAirlineManagerExist_whenUpdateAirlineManager_thenStatus204() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        airlineManager.setParkName("new_park_name");
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.parkName", is(airlineManager.getParkName())));
        airlineManagerService.deleteAirlineManager(airlineManager);
    }

    @Test
    void givenAirlineManagerExist_whenUpdateAirlineManagerWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAirlineManagerExist_whenDeleteByIdExistedAirlineManager_thenStatus200() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(delete(api + "/{id}", airlineManager.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenAirlineManagerExist_whenDeleteByIdNotExistedAirlineManager_thenStatus404() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(delete(api + "/{id}", 12310123)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        airlineManagerService.deleteAirlineManager(airlineManager);
    }

    @Test
    void whenGetAllAirlineManagerListExist_thenStatus200() throws Exception {
        List<AirlineManager> airlineManagerList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            airlineManagerList.add(airlineManagerService.createOrUpdateAirlineManager(createAirlineManager()));
        }
        mvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        airlineManagerList.forEach(airlineManager -> airlineManagerService.deleteAirlineManager(airlineManager));
        airlineManagerList.clear();
    }

    @Test
    void whenGetAllAirlineManagerListNotExist_thenStatus404() throws Exception {
        List<AirlineManager> airlineManagerList = airlineManagerService.findAll();
        airlineManagerList.forEach(airlineManagerService::deleteAirlineManager);
        mvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
