package app.controllers.rest;

import app.entities.Booking;
import app.entities.dtos.BookingDTO;
import app.entities.mappers.booking.BookingMapper;
import app.services.interfaces.BookingService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * RestController для управления записями о классе {@link Booking} в БД.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "BookingRestController")
@RequestMapping("/booking")
@Validated
public class BookingRestController {
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

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

