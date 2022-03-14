package app.config;

import app.entities.category.Category;
import app.services.category.CategoryService;
import app.services.flight.FlightService;
import app.services.seat.SeatService;
import app.entities.passenger.Passenger;
import app.entities.passenger.Passport;
import app.services.passenger.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final PassengerService passengerService;

    private final CategoryService categoryService;


    /**
     * Создание объектов категорий мест пассажиров
     */
    Category categoryEconomy = new Category("Economy");
    Category categoryComfort = new Category("Comfort");
    Category categoryBusiness = new Category("Business");
    Category categoryFirstClass = new Category("First class");


    @PostConstruct
    public void init() {
        createPassenger();
        System.out.println("Пассажир был создан.");
    }

    private void createPassenger() {
        passengerService.createOrUpdatePassenger(
                Passenger.builder()
                        .firstName("Dereck")
                        .lastName("Storm")
                        .middleName("Totoro")
                        .dateOfBirth(LocalDate.of(1992, 2, 15))
                        .email("Airlines@test.com")
                        .passport(
                                Passport.builder()
                                        .firstName("Dereck")
                                        .lastName("Storm")
                                        .middleName("Totoro")
                                        .dateOfBirth(LocalDate.of(1990, 2, 15))
                                        .gender("Male")
                                        .birthplace("US")
                                        .residenceRegistration("New York")
                                        .seriesAndNumber("3333 123456")
                                        .build()
                        )
                        .build()
        );

        /** Добавление категорий мест пассажиров */
        categoryService.createOrUpdate(categoryEconomy);
        categoryService.createOrUpdate(categoryComfort);
        categoryService.createOrUpdate(categoryBusiness);
        categoryService.createOrUpdate(categoryFirstClass);
        System.out.println("Категории добавлены");
    }

}
