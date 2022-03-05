package app.config;

import app.entities.Ticket;
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

    @PostConstruct
    public void init() {
        Ticket ticket = new Ticket("NSK", "MSK", new GregorianCalendar(2022, 03, 05)
                , new GregorianCalendar(2022, 03, 06), 18500l, "15F");


        System.out.println("DataInitializer сработал!");
    }
}
