package app.config;

import app.entities.category.Category;
import app.entities.flight.Flight;
import app.entities.seat.Seat;
import app.services.category.CategoryService;
import app.services.flight.FlightService;
import app.services.seat.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
public class DataInitializer {

    @Autowired
    SeatService seatService;
    @Autowired
    FlightService flightService;

    /**
     * Создание объекта CategoryService
     */
    final
    CategoryService categoryService;

    public DataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Создание объектов категорий мест пассажиров
     */
    Category categoryEconomy = new Category("Economy");
    Category categoryComfort = new Category("Comfort");
    Category categoryBusiness = new Category("Business");
    Category categoryFirstClass = new Category("First class");

    /** создание мест?) */

    Flight flight = new Flight(1l, "Moscow", "Tomsk");

    Seat seat1 = new Seat(1l, "1B", 1000, true, false, flight);
    Seat seat2 = new Seat(2l, "2B", 999, true, false, flight);
    Seat seat3 = new Seat(3l, "33C", 1800, true, false, flight);


    @PostConstruct
    public void init() {

        /** Добавление категорий мест пассажиров */
        categoryService.addCategory(categoryEconomy);
        categoryService.addCategory(categoryComfort);
        categoryService.addCategory(categoryBusiness);
        categoryService.addCategory(categoryFirstClass);
        System.out.println("Категории добавлены");


        System.out.println("DataInitializer сработал!");


        /** добавление мест */

        try {
            flightService.addFlight(flight);
            System.out.println("Flight added!!");
        } catch (Exception e) {
            System.out.println("Flight not added :(");
            System.out.println();
        }
        
        try {
            seatService.addSeat(seat1);
            seatService.addSeat(seat2);
            seatService.addSeat(seat3);

            System.out.println("Seat added!!");
        } catch (Exception e) {
            System.out.println("Seat not added :(");
        }
    }
}
