package app.config;


import app.entities.*;
import app.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static app.entities.Flight.builder;

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
    private final DestinationResourceService destinationResourceService;
    private final SearchService searchService;

    @PostConstruct
    public void init() {



        System.out.println("DataInitializer сработал!");

        createPassenger();
        System.out.println("Пассажир был создан.");

//        createDestinations();
//        System.out.println("Аэропорты были созданы.");

//        createAircraft();
//        System.out.println("Воздушное судно было создано.");

        createCategory();
        System.out.println("Категории были созданы");

//        createSeat();
//        System.out.println("Места были созданы");

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

        createAircraft(moscow, nizhny);
        System.out.println("Самолёт был создан");

        createSeat(moscow, nizhny);
        System.out.println("Места были созданы");

        ticketService.createOrUpdateTicket(Ticket.builder()
                .seat("5A")
                .holdNumber(420L)
                .price(15000L)
                .flight(builder()
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
//        Map<Integer, Map<DestinationResource, List<List<Route>>>> allRoutesOnlyDirect = searchService.getRoutes(listMoscowDestinationResource,
//                listDirectDestinationResource, LocalDate.of(2022, 4, 28));
//        Map<DestinationResource, List<List<Route>>> route = allRoutesOnlyDirect.get(0);
//        System.out.println(allRoutesOnlyDirect.get(0));
//        System.out.println(allRoutesOnlyDirect.get(1));
//        System.out.println(allRoutesOnlyDirect.get(2));

        Map<Integer, Map<DestinationResource, List<List<Route>>>> allRoutesOneStop = searchService.getRoutes(
                listDirectDestinationResource, listVladivostokDestinationResource, LocalDate.of(2022, 5, 27)
        );
        Map<DestinationResource, List<List<Route>>> routeOneStop = allRoutesOneStop.get(1);
        System.out.println(allRoutesOneStop.get(1));
        allRoutesOneStop.forEach((integer, destinationResourceListMap) -> {
            System.out.println(integer + ":" + destinationResourceListMap);
        });


//        Map<Integer, Map<DestinationResource, List<List<Route>>>> allRoutesDirectAndOneStop
//                = searchService.getRoutes(listMoscowDestinationResource, listFirstCityDestinationResource,
//                LocalDate.of(2022, 5, 27));

    }

    private DestinationResource createDestinationResourceMoscowDME() {
        return destinationResourceService.createOrUpdateDestinationResource(
                DestinationResource.builder()
                        .city(destinationService.getDestinationById(3L).get().getCity())
                        .airportName(destinationService.getDestinationById(3L).get().getAirportName())
                        .countryCode(destinationService.getDestinationById(3L).get().getCountryCode())
                        .countryName(destinationService.getDestinationById(3L).get().getCountryName())
                        .baseCode(destinationService.getDestinationById(3L).get().getAirportCode())
                        .availableAirportCodes(Set.of("GOJ", "OVB", "VVO", "NSK", "OMS", "DIRECT", "FIRST", "SECOND"))
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
                        .airportName("GOJ_airport")
                        .availableAirportCodes(Set.of("FIRST_TO", "OVB", "NSK", "DIRECT"))
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
                        .airportName("OMS_airport")
                        .availableAirportCodes(Set.of("FIRST_TO", "OVB", "NSK"))
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
                        .airportName("NSK_airport")
                        .availableAirportCodes(Set.of("FIRST_TO", "VVO"))
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
                        .airportName("VVO_airport")
                        .availableAirportCodes(Set.of("FIRST_TO"))
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
                        .airportName("OVB_airport")
                        .availableAirportCodes(Set.of("SECOND_TO", "VVO"))
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

//    private void createAircraft() {
//        List<Category> categories = IntStream.rangeClosed(1, 3)
//                .mapToObj(it -> Category.builder()
//                        .category("K" + it * 5)
//                        .seats(IntStream.rangeClosed(0, 10)
//                                .mapToObj(it1 ->
//                                        Seat.builder()
//                                                .seatNumber(it1 + "F")
//                                                .fare(it1)
//                                                .isRegistered(true)
//                                                .isSold(true)
//                                                .flight(Flight.builder()
//                                                        .from(destinationService.createOrUpdateDestination(
//                                                                Destination.builder()
//                                                                        .countryName("Russia")
//                                                                        .countryCode(CountryCode.RUS)
//                                                                        .city("Moscow")
//                                                                        .airportName("Domodedovo")
//                                                                        .airportCode("DME")
//                                                                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
//                                                                        .build()
//                                                        ))
//                                                        .to(destinationService.createOrUpdateDestination(
//                                                                        Destination.builder()
//                                                                                .countryName("Russia")
//                                                                                .countryCode(CountryCode.RUS)
//                                                                                .city("Nizhny Novgorod")
//                                                                                .airportName("Strigino")
//                                                                                .airportCode("GOJ")
//                                                                                .timeZone(TimeZone.getTimeZone("Europe/Nizhny Novgorod"))
//                                                                                .build()))
//                                                        .departureDate(LocalDate.of(2022, 12, 20))
//                                                        .departureTime(LocalTime.of(10, 20))
//                                                        .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
//                                                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
//                                                        .build())
//                                                .build()
//                                ).collect(Collectors.toList()))
//                        .build()
//                ).collect(Collectors.toList());
//
//        aircraftService.createOrUpdateAircraft(
//                Aircraft.builder()
//                        .categories(categories)
//                        .brand("Air")
//                        .boardNumber("RA-3030")
//                        .model("1058NS")
//                        .flyingRange(2974)
//                        .productionYear(LocalDate.of(1998, 5, 24))
//                        .build()
//        );
//    }

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
                                                .isSold(true)
                                                .flight(builder()
                                                        .from(from)
                                                        .to(to)
                                                        .departureDate(LocalDate.of(2022, 12, 20))
                                                        .departureTime(LocalTime.of(10, 20))
                                                        .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                                                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                                        .build())
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
//    private void createDestinations() {
//        destinationService.createOrUpdateDestination(
//                Destination.builder()
//                        .city("Moscow")
//                        .countryCode(CountryCode.RUS)
//                        .countryName("Russia")
//                        .airportName("Domodedovo")
//                        .airportCode("DME")
//                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
//                        .build()
//        );
//
//        destinationService.createOrUpdateDestination(
//                Destination.builder()
//                        .city("Moscow")
//                        .countryCode(CountryCode.RUS)
//                        .countryName("Russia")
//                        .airportName("Sheremetyevo")
//                        .airportCode("SVO")
//                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
//                        .build()
//        );
//
//        destinationService.createOrUpdateDestination(
//                Destination.builder()
//                        .city("Minsk")
//                        .countryCode(CountryCode.BLR)
//                        .countryName("Belarus")
//                        .airportName("Minsk")
//                        .airportCode("MSQ")
//                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
//                        .build()
//        );
//
//    }

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

//    private void createSeat() {
//        seatService.createOrUpdate(
//                Seat.builder()
//                        .seatNumber("1A")
//                        .fare(800)
//                        .isRegistered(true)
//                        .isSold(true)
//                        .flight(Flight.builder()
////                                .id(1L)
//                                        .from(destinationService.createOrUpdateDestination(
//                                                Destination.builder()
//                                                        .countryName("Russia")
//                                                        .city("Moscow")
//                                                        .countryCode(CountryCode.RUS)
//                                                        .airportName("Domodedovo")
//                                                        .airportCode("DME")
//                                                        .timeZone(TimeZone.getTimeZone("Europe/Moscow"))
//                                                        .build()))
//                                        .to(destinationService.createOrUpdateDestination(
//                                                Destination.builder()
//                                                        .countryName("Russia")
//                                                        .city("Tomsk")
//                                                        .countryCode(CountryCode.RUS)
//                                                        .airportName("Bogashevo")
//                                                        .airportCode("TOF")
//                                                        .timeZone(TimeZone.getTimeZone("Europe/Tomsk"))
//                                                        .build()
//                                        ))
//                                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
//                                        .build()
//                        ).build());
//    }

    private void createSeat(Destination from, Destination to) {
        seatService.createOrUpdate(
                Seat.builder()
                        .seatNumber("1A")
                        .fare(800)
                        .isRegistered(true)
                        .isSold(true)
                        .flight(builder()
//                                .id(1L)
                                        .from(from)
                                        .to(to)
                                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                        .build()
                        ).build());
    }

    private void createFlight() {
        flightService.createOrUpdateFlight(
                builder()
                        .from(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Norilsk")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Alykel")
                                        .airportCode("NSK")
                                        .timeZone(TimeZone.getTimeZone("Europe/KRAT"))
                                        .build()))
                        .to(destinationService.createOrUpdateDestination(
                                Destination.builder()
                                        .countryName("Russia")
                                        .city("Tomsk")
                                        .countryCode(CountryCode.RUS)
                                        .airportName("Bogashevo")
                                        .airportCode("TOF")
                                        .timeZone(TimeZone.getTimeZone("Europe/Tomsk"))
                                        .build()
                        ))
                        .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
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

}
