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
    private final RoleService roleService;
    private final DestinationResourceService destinationResourceService;
    private final SearchService searchService;
    private final SearchResultService searchResultService;

    @PostConstruct
    public void init() {
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

        Destination moscow = createMoscowDestination();
        Destination nizhny = createNizhnyDestination();
        Destination novosibirsk = createNovosibirskDestination();
        Destination vladivostok = createVladivostokDestination();
        Destination norilsk = createNorilskDestination();
        Destination omsk = createOmskDestination();
        Destination barnaul = createBarnaulDestination();
        Destination direct = createDirectDestination();

        createAircraft(moscow, nizhny);
        System.out.println("Самолёт был создан");

        ticketService.createOrUpdateTicket(Ticket.builder()
                .seat(seatService.createOrUpdate(Seat.builder()
                        .seatNumber(1 + "F")
                        .fare(1)
                        .isRegistered(false)
                        .isSold(false)
                        .build()))
                .holdNumber(420L)
                .price(15000L)
                .flight(Flight.builder()
                        .from(norilsk)
                        .to(moscow)
                        .departureDate(LocalDate.of(2022, 12, 20))
                        .departureTime(LocalTime.of(10, 20))
                        .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build())
                .build());

        DestinationResource resDME = createDestinationResourceMoscowDME();
        DestinationResource resOneStop = createDestinationResourceFirst();
        DestinationResource resTwoStops = createDestinationResourceSecond();
        DestinationResource resGoj = createDestinationResourceGOJ();
        DestinationResource resOms = createDestinationResourceOMS();
        DestinationResource resNSK = createDestinationResourceNSK();
        DestinationResource resVVO = createDestinationResourceVVO();
        DestinationResource resOVB = createDestinationResourceOVB();
        DestinationResource resDirect = createDestinationResourceDirect();

        List<DestinationResource> listMoscowDestinationResource = destinationResourceService.findByCity("Moscow");
        List<DestinationResource> listFirstCityDestinationResource = destinationResourceService.findByCity("First_city");
        List<DestinationResource> listSecondCityDestinationResource = destinationResourceService.findByCity("Second_city");
        List<DestinationResource> listNotExistedDestinationResource = destinationResourceService.findByCity("adfasdf");
        List<DestinationResource> listDirectDestinationResource = destinationResourceService.findByCity("DIRECT");
        List<DestinationResource> listVladivostokDestinationResource = destinationResourceService.findByCity("Vladivostok");

        // создаём доступные рейсы
        createFlightDmeToOms();
        createFlightDmeToOmsAfter();
        createFlightDmeToGoj();
        createFlightDmeToNsk();
        createFlightDmeToOvb();
        createFlightGojToOvb();
        createFlightGojToDme();
        createFlightOmsToOvb();
        createFlightOmsToDme();
        createFlightNskToOvb();
        createFlightVvoToOvb();
        createFlightVvoToDme();
        createFlightDmeToDirect();
        createFlightDirectToVvo();
        createFlightNskToVvo();
        createFlightDmeToSecond();
        createFlightSecondToVvo();
        // поиск доступных мест прибытий и рейсов
        List<Destination> listDest = destinationService.getDestinationListByCity("Moscow");
        List<Flight> fl = flightService.findFlights("Moscow", "Omsk",
                LocalDate.of(2022, 4,4));
        // ищем рейсы, удовлетворяющие переданным городам
        List<Flight> flightsAfterDepartureDate = flightService.findAllWithDepartureDateAfter("Moscow", "Omsk",
                LocalDate.of(2022, 4, 4));
        SearchResult searchResult1 = searchService.getSearchResultByCitiesAndLocalDates("Moscow",
                "Vladivostok", LocalDate.of(2022,4,4), LocalDate.now());
        System.out.println("DataInitializer сработал!");
    }

    private void createFlight() {
        flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("11111")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("1111")
                                        .airportCode("1111")
                                        .timeZone(TimeZone.getTimeZone("Europe/KRAT"))
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("2222")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("2222")
                                        .airportCode("2222")
                                        .timeZone(TimeZone.getTimeZone("Europe/Tomsk"))
                                        .build()
                        ))
                        .flightStatus(FlightStatus.CANCELLATION)
                        .build());
    }

    private void createPassenger() {
        passengerService.createOrUpdatePassenger(
                Passenger.builder()
                        .password("password_passenger")
                        .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
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

    private void createAdmin() {
        adminService.createOrUpdateAdmin(
                Admin.builder()
                        .email("admin@mail.com")
                        .password("password_admin")
                        .nickname("admin_nickname")
                        .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
                        .build());
    }

    private void createAirlineManager() {
        airlineManagerService.createOrUpdateAirlineManager(
                AirlineManager.builder()
                        .email("user@mail.ru")
                        .parkName("park_name")
                        .password("123")
                        .roles(Set.of(roleService.createOrUpdateRole(new Role(2L,"USER"))))
                        .build());
    }

    private Admin createAdminWithUserService() {
        return (Admin) userService.createOrUpdateUser(
                Admin.builder().email("admin@mail.ru")
                        .password("123")
                        .nickname("nickname_admin_user")
                        .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
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
                        .roles(Set.of(roleService.createOrUpdateRole(new Role(2L,"USER"))))
                        .build()
        );
    }

    private void createAirlineManagerWithUserService() {
        userService.createOrUpdateUser(
                AirlineManager.builder()
                        .email("airline_manager_user@mail.com")
                        .password("password_airline_manager_user")
                        .roles(Set.of(roleService.createOrUpdateRole(new Role(1L,"ADMIN"))))
                        .parkName("park_name_user")
                        .build());
    }

    private Flight createFlightDmeToOvb() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .airportCode("DME")
                                        .airportName("Domodedovo")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Novosibirsk")
                                        .airportName("Tolmachevo")
                                        .airportCode("OVB")
                                        .build()))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightSecondToVvo() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Second_city")
                                        .airportCode("SECOND")
                                        .airportName("Second_airport")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Vladivostok")
                                        .airportName("Knevichi")
                                        .airportCode("VVO")
                                        .build()))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightDmeToSecond() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .airportCode("DME")
                                        .airportName("Domodedovo")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Second_city")
                                        .airportName("Second_airport")
                                        .airportCode("SECOND")
                                        .build()))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightNskToVvo() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Novosibirsk")
                                        .airportCode("OVB")
                                        .airportName("Tolmachevo")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Vladivostok")
                                        .airportName("Knevichi")
                                        .airportCode("VVO")
                                        .build()))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightDirectToVvo() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Direct_city")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Direct_airport")
                                        .airportCode("DIRECT")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Vladivostok")
                                        .airportName("Knevichi")
                                        .airportCode("VVO")
                                        .build()))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightDmeToDirect() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .city("Direct_city")
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Direct_airport")
                                        .airportCode("DIRECT")
                                        .build()))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightVvoToDme() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Vladivostok")
                                        .airportName("Knevichi")
                                        .airportCode("VVO")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightVvoToOvb() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .city("Vladivostok")
                                        .airportName("Knevichi")
                                        .airportCode("VVO")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Novosibirsk")
                                        .airportCode("OVB")
                                        .airportName("Tolmachevo")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightNskToOvb() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Norilsk")
                                        .airportCode("NSK")
                                        .airportName("Alykel")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Novosibirsk")
                                        .airportCode("OVB")
                                        .airportName("Tolmachevo")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightOmsToDme() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Omsk Tsentralny")
                                        .airportCode("OMS")
                                        .city("Omsk")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightOmsToOvb() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Omsk Tsentralny")
                                        .airportCode("OMS")
                                        .city("Omsk")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Novosibirsk")
                                        .airportCode("OVB")
                                        .airportName("Tolmachevo")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());

    }

    private Flight createFlightGojToDme() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Nizhny Novgorod")
                                        .airportCode("GOJ")
                                        .airportName("Strigino")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightGojToOvb() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Nizhny Novgorod")
                                        .airportCode("GOJ")
                                        .airportName("Strigino")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Novosibirsk")
                                        .airportCode("OVB")
                                        .airportName("Tolmachevo")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightDmeToNsk() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Norilsk")
                                        .airportCode("NSK")
                                        .airportName("Alykel")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightDmeToGoj() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Nizhny Novgorod")
                                        .airportCode("GOJ")
                                        .airportName("Strigino")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private DestinationResource createDestinationResourceMoscowDME() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .city("Moscow")
                        .airportName("Domodedovo")
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .baseCode("DME")
                        .availableAirportCodes(Set.of("GOJ", "OVB", "NSK", "OMS", "DIRECT", "FIRST", "SECOND"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceDirect() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .city("Direct_city")
                        .airportName("Direct_airport")
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .baseCode("DIRECT")
                        .availableAirportCodes(Set.of("GOJ", "NSK", "OMS", "DME", "SECOND", "VVO"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceGOJ() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("Nizhny")
                        .baseCode("GOJ")
                        .airportName("Strigino")
                        .availableAirportCodes(Set.of("FIRST_TO", "OVB", "DIRECT", "DME"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceOMS() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("Omsk")
                        .baseCode("OMS")
                        .airportName("Tsentralny")
                        .availableAirportCodes(Set.of("FIRST_TO", "OVB", "DME"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceNSK() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("Norilsk")
                        .baseCode("NSK")
                        .airportName("Alykel")
                        .availableAirportCodes(Set.of("FIRST_TO", "OVB"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceVVO() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("Vladivostok")
                        .baseCode("VVO")
                        .airportName("Knevichi")
                        .availableAirportCodes(Set.of("DME", "OVB"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceOVB() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("Novosibirsk")
                        .baseCode("OVB")
                        .airportName("Tolmachevo")
                        .availableAirportCodes(Set.of("SECOND_TO", "DME", "OMS", "VVO"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceFirst() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("First_city")
                        .baseCode("FIRST")
                        .airportName("First_airport")
                        .availableAirportCodes(Set.of("FIRST_TO", "OVB", "NSK", "DIRECT"))
                        .build()
        );
    }

    private DestinationResource createDestinationResourceSecond() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .countryCode(CountryCode.RUS)
                        .countryName("Russia")
                        .city("Second_city")
                        .baseCode("SECOND")
                        .airportName("Second_airport")
                        .availableAirportCodes(Set.of("SECOND_TO", "OVB", "VVO"))
                        .build()
        );
    }

    private Destination createMoscowDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Moscow")
                        .airportName("Domodedovo")
                        .airportCode("DME")
                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
                        .build());
    }

    private Destination createNizhnyDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Nizhny Novgorod")
                        .airportName("Strigino")
                        .airportCode("GOJ")
                        .timeZone(TimeZone.getTimeZone("Europe/Nizhny Novgorod"))
                        .build());
    }

    private Destination createNovosibirskDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Novosibirsk")
                        .airportName("Tolmachevo")
                        .airportCode("OVB")
                        .timeZone(TimeZone.getTimeZone("Europe/Novosibirsk"))
                        .build());
    }

    private Destination createVladivostokDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .city("Vladivostok")
                        .airportName("Knevichi")
                        .airportCode("VVO")
                        .timeZone(TimeZone.getTimeZone("Europe/Vladivostok"))
                        .build());
    }

    private Destination createNorilskDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .city("Norilsk")
                        .countryCode(CountryCode.RUS)
                        .airportName("Alykel")
                        .airportCode("NSK")
                        .timeZone(TimeZone.getTimeZone("Europe/KRAT"))
                        .build());
    }

    private Destination createOmskDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .airportName("Omsk Tsentralny")
                        .airportCode("OMS")
                        .city("Omsk")
                        .timeZone(TimeZone.getTimeZone("Europe/OMST"))
                        .build());
    }

    private Destination createBarnaulDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .city("Barnaul")
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .airportName("German Titov")
                        .airportCode("BAX")
                        .timeZone(TimeZone.getTimeZone("MSK+4"))
                        .build());
    }

    private Destination createDirectDestination() {
        return destinationService.createOrUpdateDestination(
                Destination.builder()
                        .city("Direct_city")
                        .countryName("Russia")
                        .countryCode(CountryCode.RUS)
                        .airportName("Direct_airport")
                        .airportCode("DIRECT")
                        .build());
    }

    private void createAircraft(Destination from, Destination to) {
        List<Category> categories = IntStream.rangeClosed(1, 3)
                .mapToObj(it -> Category.builder()
                        .category("K" + it * 5)
                        .seats(IntStream.rangeClosed(0, 10)
                                .mapToObj(it1 ->
                                        Seat.builder()
                                                .seatNumber(it1 + "F")
                                                .fare(it1)
                                                .isRegistered(true)
                                                .isSold(false)
                                                .build()
                                ).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());

        aircraftService.createOrUpdateAircraft(
                Aircraft.builder()
                        .brand("Air")
                        .boardNumber("RA-3030")
                        .model("1058NS")
                        .flyingRange(2974)
                        .productionYear(LocalDate.of(1998, 5, 24))
                        .build()
        );
    }

    private Flight createFlightDmeToOms() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Omsk")
                                        .airportCode("OMS")
                                        .airportName("Tsentralny")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 4))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }

    private Flight createFlightDmeToOmsAfter() {
        return flightService.createOrUpdateFlight(
                Flight.builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Moscow")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Domodedovo")
                                        .airportCode("DME")
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryCode(CountryCode.RUS)
                                        .countryName("Russia")
                                        .city("Omsk")
                                        .airportCode("OMS")
                                        .airportName("Tsentralny")
                                        .build()
                        ))
                        .departureDate(LocalDate.of(2022, 4, 5))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                        .build());
    }
}
