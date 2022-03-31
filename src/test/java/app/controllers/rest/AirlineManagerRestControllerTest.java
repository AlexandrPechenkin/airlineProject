package app.controllers.rest;

import app.AirlineApplication;
import app.entities.AirlineManager;
import app.entities.Role;
import app.services.interfaces.AirlineManagerService;
import app.services.interfaces.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
public class AirlineManagerRestControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired @Qualifier("airlineManagerServiceImpl")
    AirlineManagerService airlineManagerService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleService roleService;

    final String api = "/api/airlineManager";
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    AirlineManager createAirlineManager() {
        return AirlineManager.builder()
                .email("airline_manager@mail.com")
                .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
                .password("airline")
                .parkName("airline_manager_park_name")
                .build();
    }

    @Test
    void whenCreateAirlineManager_thenStatus201() throws Exception {
        AirlineManager airlineManager = createAirlineManager();
        MvcResult mvcResult = mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(airlineManager.getEmail())))
                .andExpect(jsonPath("$.parkName", is(airlineManager.getParkName())))
                .andReturn();

        String password = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.password");
        assertTrue(passwordEncoder.matches(airlineManager.getPassword(), password));
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
    }

    @Test
    void givenAirlineManagerExist_whenGetByIdNotExistedAirlineManager_thenStatus404() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(get(api + "/{id}", 123123)
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAirlineManagerExist_whenUpdateAirlineManager_thenStatus200() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        airlineManager.setParkName("new_park_name");
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(airlineManager))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parkName", is(airlineManager.getParkName())));
    }

    @Test
    void givenAirlineManagerExist_whenUpdateAirlineManagerWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAirlineManagerExist_whenDeleteByIdExistedAirlineManager_thenStatus204() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(delete(api + "/{id}", airlineManager.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenAirlineManagerExist_whenDeleteByIdNotExistedAirlineManager_thenStatus404() throws Exception {
        AirlineManager airlineManager = airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        mvc.perform(delete(api + "/{id}", 12310123)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetAllAirlineManagerListExist_thenStatus200() throws Exception {
        for (int i = 0; i < 5; i++) {
            airlineManagerService.createOrUpdateAirlineManager(createAirlineManager());
        }
        mvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetAllAirlineManagerListNotExist_thenStatus404() throws Exception {
        List<AirlineManager> airlineManagerList = airlineManagerService.findAll();
        airlineManagerList.forEach(airlineManager -> airlineManagerService.deleteAirlineManagerById(airlineManager.getId()));
        mvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
