package app.controllers.rest;

import app.entities.Booking;
import app.entities.dtos.BookingDTO;
import app.entities.mappers.booking.BookingListMapper;
import app.entities.mappers.booking.BookingMapper;
import app.services.interfaces.BookingService;
import app.util.JsonFlightContainerParser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * RestController для управления записями о классе {@link Booking} в БД.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "BookingRestController")
@RequestMapping("/api/booking")
@Validated
public class BookingRestController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final JsonFlightContainerParser flightContainerParser;
    private final BookingListMapper bookingListMapper;

    /**
     * Создание записи в БД о новом бронировании пассажиром билета на рейс.
     *
     * @param booking - данные о созданном бронировании с фронтенда.
     * @return {@link ResponseEntity<BookingDTO>}
     */
    @ApiOperation(value = "Запрос для создания бронирования пассажира", notes = "Создание бронирования пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Бронирование успешно создано"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@ApiParam(value = "Booking DTO")
                                                     @RequestBody @Valid BookingDTO booking) {
        return new ResponseEntity<>
                (bookingMapper.toDto(bookingService.createOrUpdateBooking(bookingMapper.toEntity(booking))),
                        HttpStatus.CREATED);
    }

    /**
     * Получить запись о бронировании билета на рейс по id бронирования.
     *
     * @param id - уникальный идентификатор записи бронирования в БД.
     * @return {@link ResponseEntity<BookingDTO>}
     */
    @ApiOperation(value = "Запрос для получения бронирования пассажира по id бронирования",
            notes = "Получение бронирования пассажира по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Бронирование пассажира не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getById(@ApiParam(value = "id бронирования пассажира") @PathVariable Long id) {
        Optional<Booking> booking = bookingService.findById(id);

        if (booking.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookingMapper.toDto(booking.get()), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<List<BookingDTO>> constructBookings(@RequestBody @Valid String response) throws ParseException {
        List<Booking> bookings = bookingService.constructBookingsByFlightContainer(flightContainerParser.
                getFlightContainerForSetBookingPropertiesByJSONFromSearchResult(response));
        return new ResponseEntity<>(bookingListMapper.toDto(bookings), HttpStatus.OK);
    }

    /**
     * Получить запись о бронировании билета на рейс по holdNumber бронирования.
     *
     * @param holdNumber - уникальный идентификатор номера бронирования в записи бронирования в БД.
     * @return {@link ResponseEntity<BookingDTO>}
     */
    @ApiOperation(value = "Запрос для получения бронирования пассажира по номеру бронирования",
            notes = "Получение бронирования пассажира по номеру бронирования")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Бронирование пассажира не найдено")
    })
    @GetMapping("/{holdNumber}")
    public ResponseEntity<BookingDTO> getByHoldNumber(@ApiParam(value = "номер бронирования пассажира")
                                                          @PathVariable Long holdNumber) {
        List<Booking> booking = bookingService.findByHoldNumber(holdNumber);
        if (booking.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (booking.size() != 1) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(bookingMapper.toDto(booking.get(0)), HttpStatus.OK);
    }

    /**
     * Удалить запись о бронировании билета на рейс из БД.
     *
     * @param id - уникальный идентификатор записи бронирования в БД.
     * @return {@link ResponseEntity<Void>}
     */
    @ApiOperation(value = "Запрос для удаления бронирования пассажира",
            notes = "Удаление бронирования пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно удалено"),
            @ApiResponse(code = 404, message = "Бронирование пассажира не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingById(@ApiParam(example = "1", value = "ID бронирования билета рейса")
                                                              @PathVariable long id) {
        Optional<Booking> booking = bookingService.findById(id);
        if (booking.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bookingService.deleteBookingById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

