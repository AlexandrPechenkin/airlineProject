package app.config;

import app.entities.ticket.Ticket;
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

    public DataInitializer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * создание второго билета
     */
    @PostConstruct
    public void init() {
        /**
         * создание первого билета
         */
        ticketService.createTicket(Ticket.builder()
                .aircraft("Boeing 737")
                .route("NSK-MSK")
                .origin("Novosibirsk")
                .destination("Moscow")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(15, 40))
                .arrivalDate(LocalDate.of(2022, 12, 20))
                .arrivalTime(LocalTime.of(18, 00))
                .ticketPrice(19500l)
                .seat("1A")
                .build());
        /**
         * создание второго билета
         */
        ticketService.createTicket(Ticket.builder()
                .aircraft("Boeing 737")
                .route("NSK-EKB-MSK")
                .origin("Novosibirsk")
                .transfer("Ekaterinburg")
                .transferDate(LocalDate.of(2022, 12, 20))
                .transferTime(LocalTime.of(12, 40))
                .destination("Moscow")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(14, 10))
                .arrivalDate(LocalDate.of(2022, 12, 20))
                .arrivalTime(LocalTime.of(16, 30))
                .ticketPrice(16300l)
                .seat("1A")
                .build());


        System.out.println("DataInitializer сработал!");
    }
}
