package app.config;

import app.entities.flight.Flight;
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
                .seat("1A")
                .holdNumber(137l)
                .flight(Flight.builder()
                        .route(Route.builder()
                                .destinationFrom("Novosibirsk")
                                .destinationTo("Moscow")
                                .departureDate(LocalDate.of(2022, 12, 20))
                                .departureTime(LocalTime.of(15, 40))
                                .arrivalDate(LocalDate.of(2022, 12, 20))
                                .arrivalTime(LocalTime.of(18, 00))
                                .numberOfSeats(1)
                                .build())
                        .build())
                .build());
        /**
         * создание второго билета
         */
        ticketService.createTicket(Ticket.builder()
                .seat("1A")
                .holdNumber(15l)
                .flight(Flight.builder()
                        .route(Route.builder()
                                .destinationFrom("Omsk")
                                .destinationTo("Barnaul")
                                .departureDate(LocalDate.of(2022, 12, 20))
                                .departureTime(LocalTime.of(14, 10))
                                .arrivalDate(LocalDate.of(2022, 12, 20))
                                .arrivalTime(LocalTime.of(16, 30))
                                .numberOfSeats(1)
                                .build())
                        .build())
                .build());


        System.out.println("DataInitializer сработал!");
    }
}
