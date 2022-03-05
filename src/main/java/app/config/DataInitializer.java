package app.config;

import app.entities.Ticket;
import app.services.TicketService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

    //создание первого билета
    Ticket ticketFromNskToMsk = new Ticket("NSK", "MSK",
            "15.11.2022", "15:30",
            "16.11.2022", "16:30",
            18500l, "15F");

    //создание второго билета
    Ticket ticketFromTelAvivToNewYork = new Ticket("Tel-Aviv", "New York",
            "03.05.2022", "22:20",
            "04.05.2022", "04:20",
            68350l, "1A");

    @PostConstruct
    public void init() {

        ticketService.createTicket(ticketFromNskToMsk);
        ticketService.createTicket(ticketFromTelAvivToNewYork);

        System.out.println("DataInitializer сработал!");
    }
}
