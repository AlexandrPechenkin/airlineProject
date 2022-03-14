package app.controllers.rest;

import app.entities.booking.Booking;
import app.entities.booking.dto.BookingDTO;
import app.entities.searchResult.SearchResult;
import app.mappers.booking.BookingMapper;
import app.services.interfaces.BookingService;
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
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final SearchResult searchResult;

    /**
     * Бронирование создаётся, когда пользователь нажимает на кнопку "Выбрать" из списка рейсов.
     *
     * @param booking
     * @return
     */
    @ApiOperation(value = "Запрос для создания бронирования пассажира", notes = "Создание бронирования пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Бронирование успешно создано"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createOrUpdate(@ApiParam(value = "Booking DTO")
                                                        @RequestBody @Valid BookingDTO booking) {
        if (Objects.nonNull(booking.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>
                (bookingMapper.toDto(bookingService.createOrUpdate(bookingMapper.toEntity(booking))),
                        HttpStatus.CREATED);
    }

    /**
     * Получить бронирование по id.
     *
     * @param id
     * @return
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
     * Удалить бронирование.
     *
     * @param booking
     * @return
     */
    @ApiOperation(value = "Запрос для удаления бронирования пассажира",
            notes = "Удаление бронирования пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно удалено"),
            @ApiResponse(code = 404, message = "Бронирование пассажира не найдено")
    })
    @DeleteMapping()
    public ResponseEntity delete(Booking booking) {
        bookingService.delete(booking);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
