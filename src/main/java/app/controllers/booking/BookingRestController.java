package app.controllers.booking;

import app.entities.booking.Booking;
import app.entities.booking.dto.BookingDTO;
import app.mappers.booking.BookingMapper;
import app.services.booking.BookingService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Api(tags = "BookingRestController")
@RequestMapping("/booking")
@Validated
public class BookingRestController {
    private BookingService bookingService;
    private BookingMapper bookingMapper;

    @ApiOperation(value = "Запрос для создания бронирования пассажира", notes = "Создание бронирования пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Бронирование успешно создано"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@ApiParam(value = "Booking DTO")
                                                        @RequestBody @Valid BookingDTO booking) {
        if (Objects.nonNull(booking.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>
                (bookingMapper.toDto(bookingService.createOrUpdateBooking(bookingMapper.toEntity(booking))),
                        HttpStatus.CREATED);
    }

    @ApiOperation(value = "Запрос для получения бронирования пассажира по id бронирования",
                  notes = "Получение бронирования пассажира по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Бронирование пассажира не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@ApiParam(value = "id бронирования пассажира") @PathVariable Long id) {
        Optional<Booking> booking = bookingService.findById(id);

        if (booking.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookingMapper.toDto(booking.get()), HttpStatus.OK);
    }
}
