package app.config;

import app.entities.*;
import app.services.interfaces.*;
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
    private final CategoryService categoryService;
    private final SeatService seatService;
    private final FlightService flightService;
    private final AdminService adminService;
    private final AirlineManagerService airlineManagerService;
    private final UserService userService;
    private final DestinationService destinationService;

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

        createAdmin();
        System.out.println("Администратор был создан с AdminService, AdminRepository, AdminMapper, AdminDTO.");

        createAirlineManager();
        System.out.println("AirlineManager был создан c AirlineManagerService, AirlineManagerRepository, AirlineMapper, AirlineManagerDTO.");

        Admin admin = createAdminWithUserService();
        System.out.println("Администратор был создан при помощи UserService, UserRepository, AdminMapper, AdminDTO.");

        createPassengerAndPassportWithUserService();
        System.out.println("Пассажир и его паспорт был создан при помощи UserService, UserRepository, PassengerMapper, PassengerDTO.");

        createAirlineManagerWithUserService();
        System.out.println("AirlineManager был создан при помощи UserService, UserRepository, AirlineManagerMapper, AirlineManagerDTO.");
    }

    private void createPassenger() {
        passengerService.createOrUpdatePassenger(
                Passenger.builder()
                        .password("password_passenger")
                        .roles("passenger")
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

    private void createAdmin() {
        adminService.createOrUpdateAdmin(
                Admin.builder()
                        .email("admin@mail.com")
                        .password("password_admin")
                        .nickname("admin_nickname")
                        .roles("admin")
                        .build());
    }

    private void createAirlineManager() {
        airlineManagerService.createOrUpdateAirlineManager(
                AirlineManager.builder()
                        .email("airlinemanager@mail.com")
                        .parkName("park_name")
                        .password("password_airline_manager")
                        .roles("airline_manager")
                        .build());
    }

    private Admin createAdminWithUserService() {
        return (Admin) userService.createOrUpdateUser(
                Admin.builder().email("admin_user@mail.com")
                        .password("password_admin_user")
                        .nickname("nickname_admin_user")
                        .roles("admin_user")
                        .build());
    }

    private void createPassengerAndPassportWithUserService() {
        userService.createOrUpdateUser(
                Passenger.builder()
                        .firstName("Dereck_user")
                        .lastName("Storm_user")
                        .middleName("Totoro_user")
                        .dateOfBirth(LocalDate.of(1992, 2, 15))
                        .email("Airlines_user@test.com")
                        .password("password_passenger_user")
                        .passport(
                                Passport.builder()
                                        .firstName("Dereck_user")
                                        .lastName("Storm_user")
                                        .middleName("Totoro_user")
                                        .dateOfBirth(LocalDate.of(1990, 2, 15))
                                        .gender("Male_user")
                                        .birthplace("US_user")
                                        .residenceRegistration("New York_user")
                                        .seriesAndNumber("3333 123456_user")
                                        .build()
                        )
                        .roles("passenger_user")
                        .build()
        );
    }

    private void createAirlineManagerWithUserService() {
        userService.createOrUpdateUser(
                AirlineManager.builder()
                        .email("airline_manager_user@mail.com")
                        .password("password_airline_manager_user")
                        .roles("airline_manager_user")
                        .parkName("park_name_user")
                        .build());
    }

}
