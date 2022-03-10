package app.config;


import app.entities.flight.Flight;
import app.entities.flight.FlightStatus;
import app.services.flight.FlightService;
import app.services.search.SearchService;
import app.services.ticket.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
public class DataInitializer {

    SearchService searchService;
    TicketService ticketService;
    FlightService flightService;

    @Autowired
    public DataInitializer(SearchService searchService, TicketService ticketService, FlightService flightService) {
        this.searchService = searchService;
        this.ticketService = ticketService;
        this.flightService = flightService;
    }

    @PostConstruct
    public void init() {

        flightService.createOrUpdateFlight(Flight.builder()
                .from("NSK")
                .to("MSK")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(15, 30))
                .arrivalDateTime(LocalDateTime.of(2022, 12, 20, 17, 10))
                .flightStatus(FlightStatus.ACCORDING_TO_PLAN).build());

        System.out.println("DataInitializer сработал!");
    }
}
