package app.seat;

import app.AirlineApplication;
import app.entities.Category;
import app.entities.Flight;
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
public class SeatTestRestController {

    @Autowired
    MockMvc mvc;
    @Autowired
    SeatService seatService;

    final String api = "/seat";

    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    Seat createSeat1() {
        return Seat.builder()
                .id(1L)
                .seatNumber("1A")
                .fare(800)
                .isRegistered(true)
                .isSold(true)
                .category(Category.builder()
                        .id(1L)
                        .category("testCategory")
                        .build())
                .flight(Flight.builder()
                        .id(1L)
                        .destinationFrom("Moscow")
                        .destinationTo("Tomsk")
                        .build()
                ).build();
    }

    Seat createSeat2() {
        return Seat.builder()
                .id(2L)
                .seatNumber("1B")
                .fare(1100)
                .isRegistered(false)
                .isSold(true)
                .category(Category.builder()
                        .id(1L)
                        .category("testCategory")
                        .build())
                .flight(Flight.builder()
                        .id(1L)
                        .destinationFrom("Moscow")
                        .destinationTo("Tomsk")
                        .build()
                ).build();
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
