package app.controllers.v1.destination;

import app.AirlineApplication;
import app.entities.Destination;
import app.services.interfaces.DestinationService;
import app.entities.CountryCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.TimeZone;

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
public class DestinationRestControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    DestinationService destinationService;
    final String api = "/api/v1/destinations";
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Destination createDestination1() {
        return Destination.builder()
                .city("Test Moscow")
                .countryCode(CountryCode.RUS)
                .countryName("Test Russia")
                .airportName("Test Domodedovo")
                .airportCode("Test DME")
                .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                .build();
    }

    Destination createDestination2() {
        return Destination.builder()
                .city("Test Moscow")
                .countryCode(CountryCode.RUS)
                .countryName("Test Russia")
                .airportName("Test Sheremetyevo")
                .airportCode("Test SVO")
                .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                .build();
    }

    @Test
    void whenCreateDestination_thenStatus201() throws Exception {
        Destination destination = createDestination1();

        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city", is(destination.getCity())))
                .andExpect(jsonPath("$.countryCode", is("RUS")))
                .andExpect(jsonPath("$.countryName", is(destination.getCountryName())))
                .andExpect(jsonPath("$.airportName", is(destination.getAirportName())))
                .andExpect(jsonPath("$.airportCode", is(destination.getAirportCode())))
                .andExpect(jsonPath("$.timeZone", is("Europe/Moscow")));
    }

    @Test
    void whenCreateDestinationWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenDestinationExist_whenUpdateDestination_thenStatus200() throws Exception {
        Destination destination = destinationService.createOrUpdateDestination(createDestination1());

        destination.setCity("Test City");
        destination.setCountryCode(CountryCode.RUS);
        destination.setCountryName("Test Country");
        destination.setAirportName("Test Airport");
        destination.setAirportCode("Test Airport Code");
        destination.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", is(destination.getCity())))
                .andExpect(jsonPath("$.countryCode", is("RUS")))
                .andExpect(jsonPath("$.countryName", is(destination.getCountryName())))
                .andExpect(jsonPath("$.airportName", is(destination.getAirportName())))
                .andExpect(jsonPath("$.airportCode", is(destination.getAirportCode())))
                .andExpect(jsonPath("$.timeZone", is("Europe/Moscow")));
    }

    @Test
    void whenUpdateDestinationWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenDestinationExist_whenGetDestinationById_thenStatus200() throws Exception {
        Destination destination = destinationService.createOrUpdateDestination((createDestination1()));

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenDestinationExist_whenGetDestinationWithoutId_thenStatus404() throws Exception {
        Destination destination = destinationService.createOrUpdateDestination((createDestination1()));

        mvc.perform(get(api + "/{id}", 1000)
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenDestinationExist_whenGetDestinationByCity_thenStatus200() throws Exception {
        Destination destination1 = destinationService.createOrUpdateDestination((createDestination1()));
        Destination destination2 = destinationService.createOrUpdateDestination((createDestination2()));

        mvc.perform(get(api + "/city/{city}", "Test Moscow")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city", is("Test Moscow")))
                .andExpect(jsonPath("$[1].city", is("Test Moscow")));
    }

    @Test
    void givenDestinationExist_whenGetDestinationWithoutCity_thenStatus404() throws Exception {
        Destination destination = destinationService.createOrUpdateDestination((createDestination1()));

        mvc.perform(get(api + "/city/{city}", "Test Minsk")
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenDestinationExist_whenGetDestinationByCountryName_thenStatus200() throws Exception {
        Destination destination1 = destinationService.createOrUpdateDestination((createDestination1()));
        Destination destination2 = destinationService.createOrUpdateDestination((createDestination2()));

        mvc.perform(get(api + "/country/{country}", "Test Russia")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].countryName", is("Test Russia")))
                .andExpect(jsonPath("$[1].countryName", is("Test Russia")));
    }

    @Test
    void givenDestinationExist_whenGetDestinationWithoutCountryName_thenStatus404() throws Exception {
        Destination destination = destinationService.createOrUpdateDestination((createDestination1()));

        mvc.perform(get(api + "/country/{country}", "Test Belarus")
                        .content(objectMapper.writeValueAsString(destination))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
