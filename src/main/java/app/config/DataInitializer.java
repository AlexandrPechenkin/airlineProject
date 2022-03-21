package app.config;


import app.entities.flight.Flight;
import app.entities.flight.FlightStatus;
import app.services.flight.FlightService;
import app.services.search.SearchService;
import app.services.ticket.TicketService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DataInitializer {

    private final SearchService searchService;
    private final TicketService ticketService;
    private final FlightService flightService;

    @PostConstruct
    public void init() {

        flightService.createOrUpdateFlight(Flight.builder()
                .from("NSK")
                .to("MSK")
                .departureDate(LocalDate.of(2022, 12, 20))
                .arrivalDate(LocalDate.of(2022, 12, 20))
                .flightStatus(FlightStatus.ACCORDING_TO_PLAN).build());

        System.out.println("DataInitializer сработал!");
    }
}
