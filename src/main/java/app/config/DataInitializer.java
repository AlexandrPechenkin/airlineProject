package app.config;


import app.entities.*;
import app.services.interfaces.*;
import app.util.Fleet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final PassengerService passengerService;
    private final AircraftService aircraftService;
    private final CategoryService categoryService;
    private final SeatService seatService;
    private final FlightService flightService;
    private final AdminService adminService;
    private final AirlineManagerService airlineManagerService;
    private final UserService userService;
    private final DestinationService destinationService;
    private final TicketService ticketService;
    private final RegistrationService registrationService;

    @PostConstruct
    public void init() {

        Passenger passenger = Passenger.builder()
                .password("password_first")
                .roles(Set.of(new Role("ADMIN")))
                .firstName("Dereck")
                .lastName("Storm")
                .middleName("Totoro")
                .dateOfBirth(LocalDate.of(1992, 2, 15))
                .email("passanger@test.com")
                .passport(
                        Passport.builder()
                                .firstName("Dereck")
                                .lastName("Storm")
                                .middleName("Totoro")
                                .dateOfBirth(LocalDate.of(1990, 2, 15))
                                .gender("Male")
                                .birthplace("US")
                                .residenceRegistration("New York")
                                .seriesAndNumber("3333 123457")
                                .build()
                )
                .build();
        passengerService.createOrUpdatePassenger(passenger);

        Ticket ticket = ticketService.createOrUpdateTicket(Ticket.builder()
                .passenger(passenger)
                .seat("5A")
                .holdNumber(420L)
                .price(15000L)
                .flight(Flight.builder()
                        .destinationFrom("NSK")
                        .destinationTo("MSK")
                        .departureDate(LocalDate.of(2022, 12, 20))
                        .departureTime(LocalTime.of(10, 20))
                        .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build())
                .build());

        System.out.println("DataInitializer сработал!");

        aircraftService.createOrUpdateAircraft(Fleet.createMC21200());
        System.out.println("Самолет МС-21-200 был создан.");

        aircraftService.createOrUpdateAircraft(Fleet.createBoeing777());
        System.out.println("Самолет Боинг 777 был создан.");

        createPassenger();
        System.out.println("Пассажир был создан.");

        createDestinations();
        System.out.println("Аэропорты были созданы.");

        createAircraft();
        System.out.println("Воздушное судно было создано.");

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

        createRegistration(ticket);
        System.out.println("Регистрация пассажира на рейс создана.");

    }

    private void createPassenger() {
        passengerService.createOrUpdatePassenger(
                Passenger.builder()
                        .password("password_passenger")
                        .roles(Set.of(new Role("ADMIN")))
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

    private void createAircraft() {
        List<Category> categories = IntStream.rangeClosed(1, 3)
                .mapToObj(it -> Category.builder()
                        .category("K" + it * 5)
                        .seats(IntStream.rangeClosed(0, 10)
                                .mapToObj(it1 ->
                                        Seat.builder()
                                                .seatNumber(it1 + "F")
                                                .fare(it1)
                                                .isRegistered(true)
                                                .isSold(true)
                                                .build()
                                ).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());

        aircraftService.createOrUpdateAircraft(
                Aircraft.builder()
                        .categories(categories)
                        .brand("Air")
                        .boardNumber("RA-3030")
                        .model("1058NS")
                        .flyingRange(2974)
                        .productionYear(LocalDate.of(1998, 5, 24))
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
        categoryService.createOrUpdate(Category.builder()
                .category("Economy")
                .build());
        categoryService.createOrUpdate(Category.builder()
                .category("Comfort")
                .build());
        categoryService.createOrUpdate(Category.builder()
                .category("Business")
                .build());
        categoryService.createOrUpdate(Category.builder()
                .category("First class")
                .build());
    }

    private void createSeat() {
        seatService.createOrUpdate(
                Seat.builder()
                        .seatNumber("1A")
                        .fare(800)
                        .isRegistered(true)
                        .isSold(true)
                        .build());
    }

    private void createFlight() {
        flightService.createOrUpdateFlight(Flight.builder()
                .destinationFrom("NSK")
                .destinationTo("MSK")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(10, 20))
                .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                .flightStatus(FlightStatus.CANCELLATION)
                .build());
    }

    private void createAdmin() {
        adminService.createOrUpdateAdmin(
                Admin.builder()
                        .email("admin@mail.com")
                        .password("password_admin")
                        .nickname("admin_nickname")
                        .roles(Set.of(new Role("ROLE_ADMIN")))
                        .build());
    }

    private void createAirlineManager() {
        airlineManagerService.createOrUpdateAirlineManager(
                AirlineManager.builder()
                        .email("user@mail.ru")
                        .parkName("park_name")
                        .password("123")
                        .roles(Set.of(new Role("ROLE_USER")))
                        .build());
    }

    private Admin createAdminWithUserService() {
        return (Admin) userService.createOrUpdateUser(
                Admin.builder().email("admin@mail.ru")
                        .password("123")
                        .nickname("nickname_admin_user")
                        .roles(Set.of(new Role("ROLE_ADMIN")))
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
                        .roles(Set.of(new Role("USER")))
                        .build()
        );
    }

    private void createAirlineManagerWithUserService() {
        userService.createOrUpdateUser(
                AirlineManager.builder()
                        .email("airline_manager_user@mail.com")
                        .password("password_airline_manager_user")
                        .roles(Set.of(new Role("ADMIN")))
                        .parkName("park_name_user")
                        .build());
    }

    private void createRegistration(Ticket ticket) {
        registrationService.createOrUpdateOrDeleteRegistration(
                Registration.builder()
                        .ticket(ticket)
                        .status("IN_PROGRESS")
                        .registrationDateTime(LocalDateTime.now())
                        .build());
    }

}
