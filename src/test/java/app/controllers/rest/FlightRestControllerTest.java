package app.controllers.rest;

import app.AirlineApplication;
import app.entities.CountryCode;
import app.entities.Destination;
import app.entities.Flight;
import app.entities.FlightStatus;
import app.services.interfaces.DestinationService;
import app.services.interfaces.FlightService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class FlightRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    FlightService flightService;
    @Autowired
    DestinationService destinationService;

    final String api = "/api/flight";

    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Flight createFlight() {
        return flightService.createOrUpdateFlight(Flight.builder()
                .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Norilsk")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Alykel")
                                        .airportCode("NSK")
                                        .timeZone(TimeZone.getTimeZone("Europe/KRAT"))
                                        .build()))
                .to(destinationService.createOrUpdateDestination(
                        Destination.builder()
                                .countryName("Russia")
                                .city("Moscow")
                                .countryCode(CountryCode.RUS)
                                .airportName("Domodedovo")
                                .airportCode("DME")
                                .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                                .build()
                ))
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(10, 20))
                .arrivalDateTime(LocalDateTime.of(2022, 12, 20, 15, 40))
                .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                .build());
    }

    @Test
    void whenCreateFlightWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void whenCreateFlight_thenStatus201() throws Exception {
        Flight flight = createFlight();

        assert flight.getTo() != null;
        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(flight))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.from.city", is(flight.getFrom().getCity())))
                .andExpect(jsonPath("$.to.city", is(flight.getTo().getCity())));

    }

    @Test
    void givenFlightExist_whenUpdateFlight_thenStatus200() throws Exception {
        Flight flight = flightService.createOrUpdateFlight(createFlight());
        flight.setFrom(destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .airportName("Omsk Tsentralny")
                        .airportCode("OMS")
                        .city("Omsk")
                        .timeZone(TimeZone.getTimeZone("Europe/OMST"))
                        .build()
        ));
        flight.setTo(destinationService.createOrUpdateDestination(
                Destination.builder()
                        .city("Barnaul")
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .airportName("German Titov")
                        .airportCode("BAX")
                        .timeZone(TimeZone.getTimeZone("MSK+4"))
                        .build()
        ));
        assert flight.getTo() != null;
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(flight))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from.city", is(flight.getFrom().getCity())))
                .andExpect(jsonPath("$.to.city", is(flight.getTo().getCity())));
    }

    @Test
    void whenUpdateFlightWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenFlightExist_whenGetByIdFlight_thenStatus200() throws Exception {
        Flight flight = flightService.createOrUpdateFlight(createFlight());

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(flight))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenFlightExist_whenGetWithoutIdFlight_thenStatus404() throws Exception {
        Flight flight = flightService.createOrUpdateFlight(createFlight());

        mvc.perform(get(api + "/{id}", 420)
                        .content(objectMapper.writeValueAsString(flight))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
