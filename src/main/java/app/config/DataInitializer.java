package app.config;


import app.entities.Flight;
import app.entities.FlightStatus;
import app.entities.Ticket;
import app.services.interfaces.FlightService;
import app.services.interfaces.SearchService;
import app.services.interfaces.TicketService;
import lombok.RequiredArgsConstructor;
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


        ticketService.createOrUpdateTicket(Ticket.builder()
                .seat("5A")
                .holdNumber(420l)
                .price(15000l)
                .flight(Flight.builder()
                        .from("NSK")
                        .to("MSK")
                        .departureDate(LocalDate.of(2022, 12, 20))
                        .departureTime(LocalTime.of(10, 20))
                        .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build())
                .build());

        System.out.println("DataInitializer сработал!");
    }
}
