package app.util;

import app.entities.*;
import app.services.interfaces.DestinationService;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Утилитный класс для создания различных видов самолётов
 */
@UtilityClass
public class Fleet {
    DestinationService destinationService;
    public static Aircraft createMC21200() {
        Category businessClass = Category.builder()
                .category("Бизнес-класс")
                .seats(IntStream.rangeClosed(1, 12)
                        .mapToObj(iter ->
                                Seat.builder()
                                        .seatNumber("БК" + iter)
                                        .fare(6500)
                                        .isRegistered(false)
                                        .isSold(false)
                                        .flight(Flight.builder()
                                                .from(destinationService.getDestinationById(1L).get())
                                                .to(destinationService.getDestinationById(2L).get())
                                                .departureDate(LocalDate.of(2022, 12, 20))
                                                .departureTime(LocalTime.of(10, 20))
                                                .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                                                .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                                .build())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();
        Category economyClass = Category.builder()
                .category("Эконом-класс")
                .seats(IntStream.rangeClosed(1, 120)
                        .mapToObj(iter ->
                                Seat.builder()
                                        .seatNumber("ЭК" + iter)
                                        .fare(3600)
                                        .isRegistered(false)
                                        .isSold(false)
                                        .flight(Flight.builder()
                                                .from(destinationService.getDestinationById(1L).get())
                                                .to(destinationService.getDestinationById(2L).get())
                                                .departureDate(LocalDate.of(2022, 12, 20))
                                                .departureTime(LocalTime.of(10, 20))
                                                .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                                                .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                                .build())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();

        return Aircraft.builder()
                .model("МС-21-200")
                .brand("Иркут")
                .boardNumber("73000")
                .flyingRange(6400)
                .productionYear(LocalDate.of(2024, 1, 1))
                .categories(List.of(businessClass, economyClass))
                .build();
    }

    public static Aircraft createBoeing777() {
        Category businessClass = Category.builder()
                .category("Бизнес-класс")
                .seats(IntStream.rangeClosed(1, 28)
                        .mapToObj(iter ->
                                Seat.builder()
                                        .seatNumber("БК" + iter)
                                        .fare(6500)
                                        .isRegistered(false)
                                        .isSold(false)
                                        .flight(Flight.builder()
                                                .from(destinationService.getDestinationById(1L).get())
                                                .to(destinationService.getDestinationById(2L).get())
                                                .departureDate(LocalDate.of(2022, 12, 20))
                                                .departureTime(LocalTime.of(10, 20))
                                                .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                                                .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                                .build())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();
        Category comfortClass = Category.builder()
                .category("Комфорт-класс")
                .seats(IntStream.rangeClosed(1, 24)
                        .mapToObj(iter ->
                                Seat.builder()
                                        .seatNumber("КК" + iter)
                                        .fare(4600)
                                        .isRegistered(false)
                                        .isSold(false)
                                        .flight(Flight.builder()
                                                .from(destinationService.getDestinationById(1L).get())
                                                .to(destinationService.getDestinationById(2L).get())
                                                .departureDate(LocalDate.of(2022, 12, 20))
                                                .departureTime(LocalTime.of(10, 20))
                                                .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                                                .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                                .build())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();
        Category economyClass = Category.builder()
                .category("Эконом-класс")
                .seats(IntStream.rangeClosed(1, 375)
                        .mapToObj(iter ->
                                Seat.builder()
                                        .seatNumber("ЭК" + iter)
                                        .fare(3200)
                                        .isRegistered(false)
                                        .isSold(false)
                                        .flight(Flight.builder()
                                                .from(destinationService.getDestinationById(1L).get())
                                                .to(destinationService.getDestinationById(2L).get())
                                                .departureDate(LocalDate.of(2022, 12, 20))
                                                .departureTime(LocalTime.of(10, 20))
                                                .arrivalDateTime(LocalDateTime.of(2022, 12, 21, 14, 40))
                                                .flightStatus(FlightStatus.ACCORDING_TO_PLAN)
                                                .build())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();

        return Aircraft.builder()
                .model("Боинг B777")
                .brand("Аэрофлот")
                .boardNumber("73100")
                .flyingRange(11200)
                .productionYear(LocalDate.of(2019, 5, 15))
                .categories(List.of(businessClass, comfortClass, economyClass))
                .build();
    }
}
