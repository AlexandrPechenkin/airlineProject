package app.controllers.rest;

import app.AirlineApplication;
import app.entities.Seat;
import app.services.interfaces.SeatService;
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
public class SeatRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    SeatService seatService;

    static final String api = "/api/seat";

    static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    Seat createSeat1() {
        return Seat.builder()
                .id(1L)
                .seatNumber("1A")
                .fare(800)
                .isRegistered(true)
                .isSold(true)
//                .flight(Flight.builder()
//                        .id(1L)
//                        .flightStatus(FlightStatus.DELAY)
//                        .from(Destination.builder()
//                                .airportName("seat1_airport_name")
//                                .airportCode("seat1_airport_code")
//                                .city("Moscow")
//                                .countryName("Russia")
//                                .countryCode(CountryCode.RUS)
//                                .build())
//                        .to(Destination.builder()
//                                .airportName("Bogashevo")
//                                .airportCode("TOF")
//                                .countryName("Russia")
//                                .countryCode(CountryCode.RUS)
//                                .city("Tomsk")
//                                .build())
//                        .build()
                .build();
    }

    Seat createSeat2() {
        return Seat.builder()
                .id(2L)
                .seatNumber("1B")
                .fare(1100)
                .isRegistered(false)
                .isSold(true)
//                .flight(Flight.builder()
//                        .id(1L)
//                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
//                        .from(Destination.builder()
//                                .airportName("seat1_airport_name")
//                                .airportCode("seat1_airport_code")
//                                .city("Moscow")
//                                .countryName("Russia")
//                                .countryCode(CountryCode.RUS)
//                                .build())
//                        .to(Destination.builder()
//                                .airportName("Bogashevo")
//                                .airportCode("TOF")
//                                .countryName("Russia")
//                                .countryCode(CountryCode.RUS)
//                                .city("Tomsk")
//                                .build())
//                        .build()
                .build();
    }


    @Test
    void whenCreateSeat_thenStatus201() throws Exception {
        Seat seat = createSeat1();

        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(seat))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seatNumber", is(seat.getSeatNumber())))
                .andExpect(jsonPath("$.fare", is(seat.getFare())))
                .andExpect(jsonPath("$.isRegistered", is(seat.getIsRegistered())))
                .andExpect(jsonPath("$.isSold", is(seat.getIsSold())));
    }

    @Test
    void whenCreateSeatWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void givenSeatExist_whenUpdateSeat_thenStatus200() throws Exception {
        Seat seat = seatService.createOrUpdate(createSeat1());
        seat.setFare(6000);
        seat.setIsRegistered(false);
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(seat))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fare", is(seat.getFare())))
                .andExpect(jsonPath("$.isRegistered", is(seat.getIsRegistered())))
                .andExpect(jsonPath("$.isSold", is(seat.getIsSold())))
                .andExpect(jsonPath("$.seatNumber", is(seat.getSeatNumber())));
    }


    @Test
    void whenUpdateSeatWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void givenSeatExist_whenGetByIdFlight_thenStatus200() throws Exception {
        Seat seat1 = seatService.createOrUpdate(createSeat1());
        Seat seat2 = seatService.createOrUpdate(createSeat2());

        mvc.perform(get(api + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].seatNumber", is(seat1.getSeatNumber())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].seatNumber", is(seat2.getSeatNumber())));
    }

    @Test
    void givenSeatExist_whenGetWithoutIdSeat_thenStatus404() throws Exception {
        Seat seat1 = seatService.createOrUpdate(createSeat1());

        mvc.perform(get(api + "/{id}", 100500)
                        .content(objectMapper.writeValueAsString(seat1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void givenSeatExist_whenGetByIdFlightAndIdCategory_thenStatus200() throws Exception {
        Seat seat1 = seatService.createOrUpdate(createSeat1());
        Seat seat2 = seatService.createOrUpdate(createSeat2());

        mvc.perform(get(api + "/{idFlight}" + "/{idCategory}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].seatNumber", is(seat1.getSeatNumber())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].seatNumber", is(seat2.getSeatNumber())));
    }


    @Test
    void givenSeatExist_whenGetWithoutIdIdFlightAndIdCategory_thenStatus404() throws Exception {
        Seat seat1 = seatService.createOrUpdate(createSeat1());

        mvc.perform(get(api + "/{idFlight}" + "/{idCategory}", 100500, 100500)
                        .content(objectMapper.writeValueAsString(seat1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void givenSeatExist_whenGetCountNoSoldSeatByIdFlight_thenStatus200() throws Exception {

        mvc.perform(get(api + "/no-sold/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenSeatExist_whenGetCountNoSoldSeatWithoutIdFlight_thenStatus200() throws Exception {
        mvc.perform(get(api + "/no-sold/{idFlight}", 100500)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void givenSeatExist_whenGetCountSoldSeatByIdFlight_thenStatus200() throws Exception {

        mvc.perform(get(api + "/sold/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenSeatExist_whenGetCountSoldSeatWithoutIdFlight_thenStatus200() throws Exception {
        mvc.perform(get(api + "/sold/{idFlight}", 100500)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void givenSeatExist_whenGetCountRegistratedSeatByIdFlight_thenStatus200() throws Exception {

        mvc.perform(get(api + "/registered/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenSeatExist_whenGetCountRegistratedSeatWithoutIdFlight_thenStatus200() throws Exception {
        mvc.perform(get(api + "/registered/{id}", 100500)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
