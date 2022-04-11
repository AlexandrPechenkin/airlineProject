package app.controllers.rest;

import app.AirlineApplication;
import app.entities.*;
import app.services.interfaces.DestinationService;
import app.services.interfaces.TicketService;
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
public class TicketRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    TicketService ticketService;
    @Autowired
    DestinationService destinationService;

    final String api = "/api/ticket";

    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Ticket createTicket() {
        return ticketService.createOrUpdateTicket(Ticket.builder()
                .seat(Seat.builder()
                        .seatNumber(2 + "F")
                        .fare(2)
                        .isRegistered(true)
                        .isSold(true)
                        .build())
                .holdNumber(420l)
                .price(15000l)
                .flight(Flight.builder()
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
                        .build())
                .build());
    }

    @Test
    void whenCreateTicketWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenCreateTicket_thenStatus201() throws Exception {
        Ticket ticket = createTicket();

        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(ticket))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.seat", is(ticket.getSeat())));
    }

    @Test
    void givenTicketExist_whenUpdateTicket_thenStatus200() throws Exception {
        Ticket ticket = ticketService.createOrUpdateTicket(createTicket());
        ticket.setSeat(Seat.builder()
                .seatNumber(3 + "F")
                .fare(3)
                .isRegistered(true)
                .isSold(true)
                .build());
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(ticket))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seat", is(ticket.getSeat())));
    }

    @Test
    void whenUpdateTicketWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenTicketExist_whenGetByIdTicket_thenStatus200() throws Exception {
        Ticket ticket = ticketService.createOrUpdateTicket(createTicket());

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(ticket))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenTicketExist_whenGetWithoutIdTicket_thenStatus404() throws Exception {
        Ticket ticket = ticketService.createOrUpdateTicket(createTicket());

        mvc.perform(get(api + "/{id}", 420)
                        .content(objectMapper.writeValueAsString(ticket))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
