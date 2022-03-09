package app.config;

import app.entities.route.Route;
import app.entities.ticket.Ticket;
import app.services.route.RouteService;
import app.services.ticket.TicketService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
public class DataInitializer {

    TicketService ticketService;
    RouteService routeService;

    public DataInitializer(TicketService ticketService, RouteService routeService) {
        this.ticketService = ticketService;
        this.routeService = routeService;
    }

    @PostConstruct
    public void init() {

        /**
         * создание первого билета
         */
        ticketService.createTicket(Ticket.builder()
                .aircraft("Boeing 737")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(15, 40))
                .arrivalDate(LocalDate.of(2022, 12, 20))
                .arrivalTime(LocalTime.of(18, 00))
                .ticketPrice(19500l)
                .seat("1A")
                .route(Route.builder()
                        .route("S455")
                        .origin("Novosibirsk")
                        .destination("Moscow")
                        .build())
                .build());
        /**
         * создание второго билета
         */
        ticketService.createTicket(Ticket.builder()
                .aircraft("Boeing 737")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(14, 10))
                .arrivalDate(LocalDate.of(2022, 12, 20))
                .arrivalTime(LocalTime.of(16, 30))
                .ticketPrice(16300l)
                .seat("1A")
                .route(Route.builder()
                        .route("S3557")
                        .origin("Omsk")
                        .destination("Barnaul")
                        .build())
                .build());


        System.out.println("DataInitializer сработал!");
    }
}
