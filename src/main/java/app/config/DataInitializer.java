package app.config;


import app.entities.aircraft.Aircraft;
import app.entities.booking.Booking;
import app.entities.category.Category;
import app.entities.flight.Flight;
import app.entities.flight.FlightStatus;
import app.entities.passenger.Passenger;
import app.entities.passenger.Passport;
import app.entities.seat.Seat;
import app.entities.ticket.Ticket;
import app.services.interfaces.BookingService;
import app.services.category.CategoryService;
import app.services.flight.FlightService;
import app.services.interfaces.PassengerService;
import app.services.interfaces.RegistrationService;
import app.services.search.SearchService;
import app.services.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

//    public DataInitializer(SearchService searchService, TicketService ticketService, FlightService flightService,
//                           PassengerService passengerService, BookingService bookingService,
//                           RegistrationService registrationService, CategoryService categoryService) {
//        this.searchService = searchService;
//        this.ticketService = ticketService;
//        this.flightService = flightService;
//        this.passengerService = passengerService;
//        this.bookingService = bookingService;
//        this.registrationService = registrationService;
//        this.categoryService = categoryService;
//    }

    private final SearchService searchService;
    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final BookingService bookingService;
    private final RegistrationService registrationService;
    private final CategoryService categoryService;

    @PostConstruct
    public void init() {

        flightService.createOrUpdateFlight(Flight.builder()
                .from("NSK")
                .to("MSK")
                .departureDate(LocalDate.of(2022, 12, 20))
                .departureTime(LocalTime.of(15, 30))
                .arrivalDateTime(LocalDateTime.of(2022, 12, 20, 17, 10))
                .flightStatus(FlightStatus.ACCORDING_TO_PLAN).build());
        Flight flight = flightService.getFlightById(1L);

        flightService.createOrUpdateFlight(Flight.builder()
                .from("MSK")
                .to("NSK")
                .departureDate(LocalDate.of(2023, 12, 20))
                .departureTime(LocalTime.of(10, 30))
                .arrivalDateTime(LocalDateTime.of(2023, 12, 2, 10, 10))
                .flightStatus(FlightStatus.DELAY).build());
        Flight flightReturn = flightService.getFlightById(2L);

        passengerService.createOrUpdatePassenger(Passenger.builder()
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
                                .seriesAndNumber("1234 567890")
                                .build()
                )
                .build());
        Optional<Passenger> passengerOptional = passengerService.findById(1L);
        Passenger passenger = passengerOptional.get();

        ticketService.createTicket(Ticket.builder()
                        .flight(Flight.builder()
                                .from("MSK")
                                .to("NSK")
                                .departureDate(LocalDate.of(2023, 12, 20))
                                .departureTime(LocalTime.of(10, 30))
                                .arrivalDateTime(LocalDateTime.of(2023, 12, 2, 10, 10))
                                .flightStatus(FlightStatus.DELAY).build())
                        .holdNumber(22L)
                        .price(100L)
                        .seat("seat")
                .build());

        Ticket ticket = ticketService.getTicketByID(1L);

        List<Seat> aircraftSeats = new ArrayList<>();
        Seat seat1 = new Seat(1L, "seatNumber1", 10, flight, true, true);
        Seat seat2 = new Seat(2L, "seatNumber2", 10, flight, true, true);
        Seat seat3 = new Seat(3L, "seatNumber2", 10, flight, true, true);
        Seat seat4 = new Seat(4L, "seatNumber2", 10, flight, true, true);
        Seat seat5 = new Seat(5L, "seatNumber2", 10, flight, true, true);
        aircraftSeats.add(seat1);
        aircraftSeats.add(seat2);
        aircraftSeats.add(seat3);
        aircraftSeats.add(seat4);
        aircraftSeats.add(seat5);

        Category category = new Category("ECONOMY");
        categoryService.addCategory(category);
        Map<Category, List<Seat>> cabinConfig = new HashMap<>();
        cabinConfig.put(category, aircraftSeats);
        Aircraft aircraft = new Aircraft(1L, "hullNumber", "brand", "model",
                2010, cabinConfig, 2000);

        Map<Passenger, List<Flight>> flightsDepart = new HashMap<>();
        List<Flight> flightsDepartList = new ArrayList<>();
        flightsDepartList.add(flight);
        flightsDepart.put(passenger, flightsDepartList);

        Map<Passenger, List<Flight>> flightsReturn = new HashMap<>();
        List<Flight> flightReturnList = new ArrayList<>();
        flightReturnList.add(flightReturn);
        flightsReturn.put(passenger, flightReturnList);

        bookingService.createOrUpdate(Booking.builder()
                        .initialBookingDateTime(LocalDateTime.now())
                        .paymentMethod("card")
//                        .passengerFlightsDepart()
//                        .passengerFlightsReturn(flightsReturn)
                        .status("PAID")
                .build());

        System.out.println("DataInitializer сработал!");
    }
}
