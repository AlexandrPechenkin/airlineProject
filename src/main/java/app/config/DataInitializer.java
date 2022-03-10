package app.config;

import app.entities.flight.Flight;
import app.entities.route.Route;
import app.entities.ticket.Ticket;
import app.services.flight.FlightService;
import app.services.route.RouteService;
import app.services.search.SearchService;
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

    SearchService searchService;
    TicketService ticketService;
    FlightService flightService;
    RouteService routeService;

    public DataInitializer(RouteService routeService, SearchService searchService, TicketService ticketService, FlightService flightService) {
        this.routeService = routeService;
        this.searchService = searchService;
        this.ticketService = ticketService;
        this.flightService = flightService;
    }

    @PostConstruct
    public void init() {

        /**
         * создание первого маршрута
         */
        routeService.createRoute(Route.builder()
                .from("NSK")
                .to("MSK")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(14, 10))
                .arrivalDate(LocalDate.of(2022, 12, 20))
                .arrivalTime(LocalTime.of(16, 30))
                .numberOfSeats(1)
                .build());

        System.out.println("DataInitializer сработал!");
    }
}
