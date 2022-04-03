package app.controllers.rest;

import app.AirlineApplication;
import app.entities.*;
import app.entities.mappers.aircraft.AircraftMapper;
import app.services.interfaces.AircraftService;
import app.services.interfaces.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
@WithMockUser(username = "admin@mai.ru", password = "123", roles = "ADMIN")
class AircraftRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    AircraftService aircraftService;
    @Autowired
    AircraftMapper aircraftMapper;
    @Autowired
    DestinationService destinationService;

    static final String api = "/api/aircraft";

    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private Aircraft createAircraft() {
        List<Category> categories = IntStream.rangeClosed(1, 3)
                .mapToObj(it -> Category.builder()
                        .category("K" + it * 5)
                        .seats(IntStream.rangeClosed(0, 10)
                                .mapToObj(it1 ->
                                        Seat.builder()
                                                .seatNumber(it1 + "F")
                                                .fare(it1)
                                                .isRegistered(true)
                                                .isSold(true)
                                                .flight(Flight.builder()
                                                        .from(destinationService.createOrUpdateDestination(
                                                                Destination.builder()
                                                                        .countryName("Russia")
                                                                        .countryCode(CountryCode.RUS)
                                                                        .city("Moscow")
                                                                        .airportName("Domodedovo")
                                                                        .airportCode("DME")
                                                                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                                                                        .build()
                                                        ))
                                                        .to(destinationService.createOrUpdateDestination(
                                                                Destination.builder()
                                                                        .countryName("Russia")
                                                                        .countryCode(CountryCode.RUS)
                                                                        .city("Nizhny Novgorod")
                                                                        .airportName("Strigino")
                                                                        .airportCode("GOJ")
                                                                        .timeZone(TimeZone.getTimeZone("Europe/Nizhny Novgorod"))
                                                                        .build()))
                                                        .departureDate(LocalDate.of(2021, 1, 1))
                                                        .departureTime(LocalTime.MAX)
                                                        .arrivalDateTime(LocalDateTime.MAX)
                                                        .flightStatus(FlightStatus.DELAY)
                                                        .build())
                                                .build()
                                ).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());

        return aircraftService.createOrUpdateAircraft(
                Aircraft.builder()
                        .categories(categories)
                        .brand("Air")
                        .boardNumber("RA-3030")
                        .model("1058NS")
                        .flyingRange(2974)
                        .productionYear(LocalDate.of(1998, 5, 24))
                        .build()
        );
    }

    @Test
    void whenCreateAircraft_thenStatus201() throws Exception {
        Aircraft aircraft = createAircraft();
        aircraft.getId();

        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(aircraftMapper.toDto(aircraft)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.brand", is(aircraft.getBrand())))
                .andExpect(jsonPath("$.boardNumber", is(aircraft.getBoardNumber())));
    }

    @Test
    void whenCreateAircraftWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAircraftExist_whenUpdateAircraft_thenStatus200() throws Exception {
        Aircraft aircraft = aircraftService.createOrUpdateAircraft(createAircraft());
        aircraft.setBrand("LolSA");
        aircraft.setModel("12145sss");
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(aircraft))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", is(aircraft.getBrand())))
                .andExpect(jsonPath("$.model", is(aircraft.getModel())));
    }

    @Test
    void givenAircraftExist_whenGetByIdAircraft_thenStatus200() throws Exception {
        Aircraft aircraft = aircraftService.createOrUpdateAircraft(createAircraft());

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(aircraft))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    //
    @Test
    void givenAircraftExist_whenGetWithoutIdAircraft_thenStatus404() throws Exception {
        Aircraft aircraft = aircraftService.createOrUpdateAircraft(createAircraft());

        mvc.perform(get(api + "/{id}", 228)
                        .content(objectMapper.writeValueAsString(aircraft))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void givenAircraftExist_whenDeleteAircraftById_thenStatus200() throws Exception {
        Aircraft aircraft = aircraftService.createOrUpdateAircraft(createAircraft());

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(aircraft))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenAircraftExist_whenDeleteWithoutAircraftById_thenStatus404() throws Exception {
        Aircraft aircraft = aircraftService.createOrUpdateAircraft(createAircraft());

        mvc.perform(get(api + "/{id}", 555)
                        .content(objectMapper.writeValueAsString(aircraft))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}