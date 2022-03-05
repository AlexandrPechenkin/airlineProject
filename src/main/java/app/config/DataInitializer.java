package app.config;

import app.entities.Ticket;
import app.services.TicketService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.GregorianCalendar;

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

    Ticket ticket = new Ticket("NSK", "MSK", new GregorianCalendar(2022, 03, 05, 15, 15, 15)
            , new GregorianCalendar(2022, 03, 06, 14, 13, 12), 18500l, "15F");

    @PostConstruct
    public void init() {


        ticketService.createTicket(ticket);


        System.out.println("DataInitializer сработал!");
    }
}
