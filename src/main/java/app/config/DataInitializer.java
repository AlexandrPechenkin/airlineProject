package app.config;

import app.entities.destination.Destination;
import app.entities.passenger.Passenger;
import app.entities.passenger.Passport;
import app.services.interfaces.DestinationService;
import app.entities.Category;
import app.entities.Flight;
import app.entities.Seat;
import app.services.interfaces.CategoryService;
import app.entities.Passenger;
import app.entities.Passport;
import app.services.interfaces.FlightService;
import app.services.interfaces.SeatService;
import app.services.interfaces.PassengerService;
import app.util.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.TimeZone;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */

@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final PassengerService passengerService;
    /**
     * Интерфейс сервиса для работы с аэропортом
     */
    private final DestinationService destinationService;

    private final CategoryService categoryService;

    private final SeatService seatService;

    private final FlightService flightService;

    @PostConstruct
    public void init() {
        System.out.println("DataInitializer сработал!");

        createPassenger();
        System.out.println("Пассажир был создан.");

        createDestinations();
        System.out.println("Аэропорты были созданы.");

        createCategory();
        System.out.println("Категории были созданы");

        createSeat();
        System.out.println("Места были созданы");

        createFlight();
        System.out.println("Рейс был добавлен");
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
    }

    /**
     * Создает аэропорты
     */
    private void createDestinations() {
        destinationService.createOrUpdateDestination(
                Destination.builder()
                        .city("Moscow")
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .airportName("Domodedovo")
                        .airportCode("DME")
                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                        .build()
        );

        destinationService.createOrUpdateDestination(
                Destination.builder()
                        .city("Moscow")
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .airportName("Sheremetyevo")
                        .airportCode("SVO")
                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                        .build()
        );

        destinationService.createOrUpdateDestination(
                Destination.builder()
                        .city("Minsk")
                        .countryCode(CountryCode.BLR)
                        .countryName("Belarus")
                        .airportName("Minsk")
                        .airportCode("MSQ")
                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                        .build()
        );

    }

    private void createCategory() {

        Category categoryEconomy = new Category("Economy");
        Category categoryComfort = new Category("Comfort");
        Category categoryBusiness = new Category("Business");
        Category categoryFirstClass = new Category("First class");

        categoryService.createOrUpdate(categoryEconomy);
        categoryService.createOrUpdate(categoryComfort);
        categoryService.createOrUpdate(categoryBusiness);
        categoryService.createOrUpdate(categoryFirstClass);
    }

    private void createSeat() {
        seatService.createOrUpdate(
                Seat.builder()
                        .seatNumber("1A")
                        .fare(800)
                        .isRegistered(true)
                        .isSold(true)
                        .category(Category.builder()
                                .id(1L)
                                .category("testCategory")
                                .build())
                        .flight(Flight.builder()
//                                .id(1L)
                                .destinationFrom("Moscow")
                                .destinationTo("Tomsk")
                                .build()
                        ).build());
    }

    private void createFlight() {
        flightService.createOrUpdate(
                Flight.builder()
                        .destinationFrom("Moscow")
                        .destinationTo("Tomsk")
                        .build());
    }

}
