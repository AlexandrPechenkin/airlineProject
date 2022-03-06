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
            "15.11.2022", "16:50",
            18500l, "15F");

    //создание второго билета
    Ticket ticketFromTelAvivToNewYork = new Ticket("Tel-Aviv", "New York",
            "03.05.2022", "22:20",
            "04.05.2022", "04:20",
            68350l, "1A");

    //создание третьего билета
    Ticket ticketFromNskToMsk2 = new Ticket("NSK", "MSK",
            "17.11.2022", "23:00",
            "18.11.2022", "00:20",
            16500l, "22B");

    //создание четвертого билета
    Ticket ticketFromMskToNsk = new Ticket("MSK", "NSK",
            "20.11.2022", "15:30",
            "20.11.2022", "16:50",
            20500l, "10F");


    @PostConstruct
    public void init() {

        ticketService.createTicket(ticketFromNskToMsk);
        ticketService.createTicket(ticketFromTelAvivToNewYork);
        ticketService.createTicket(ticketFromNskToMsk2);
        ticketService.createTicket(ticketFromMskToNsk);

        System.out.println("DataInitializer сработал!");
    }
}
