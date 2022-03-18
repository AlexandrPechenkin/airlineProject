package app.controllers.rest;

import app.entities.Passenger;
import app.entities.dtos.PassengerDTO;
import app.entities.mappers.PassengerMapper;
import app.services.interfaces.PassengerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * Rest контроллер для работы с пассажирами
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "PassengerController")
@RequestMapping("/api/v1/passenger")
public class PassengerRestController {
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;

    /**
     * Метод создает пассажира
     *
     * @param passenger - Пассажир
     * @return {@link PassengerDTO}
     */
    @ApiOperation(value = "Запрос для создания пассажира", notes = "Создание пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пассажир успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<PassengerDTO> createPassenger(@ApiParam(value = "DTO Пассажира") @RequestBody @Valid PassengerDTO passenger) {
        if (Objects.nonNull(passenger.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                passengerMapper.toDto(
                        passengerService.createOrUpdatePassenger(
                                passengerMapper.toEntity(passenger))), HttpStatus.CREATED);
    }

    /**
     * Метод обновляет пассажира
     *
     * @param passenger - Пассажир
     * @return {@link PassengerDTO}
     */
    @ApiOperation(value = "Запрос для обновления пассажира", notes = "Обновление пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пассажир успешно обновлен"),
            @ApiResponse(code = 400, message = "Переданы неверные данные"),
    })
    @PutMapping
    public ResponseEntity<PassengerDTO> updatePassenger(@ApiParam(value = "DTO Пассажира") @RequestBody @Valid PassengerDTO passenger) {
        return new ResponseEntity<>(
                passengerMapper.toDto(
                        passengerService.createOrUpdatePassenger(
                                passengerMapper.toEntity(passenger))), HttpStatus.OK);

    }

    /**
     * Метод получает пассажира по уникальному идентификатору
     *
     * @param id - Уникальный идентификатор
     * @return {@link PassengerDTO}
     */
    @ApiOperation(value = "Запрос для получения пассажира по ip", notes = "Возвращение пассажира по ip")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Пассажир не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDTO> getPassengerById(@ApiParam(value = "ID Пассажира") @PathVariable Long id) {
        Optional<Passenger> passenger = passengerService.findById(id);

        if (passenger.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(passengerMapper.toDto(passenger.get()), HttpStatus.OK);
        }
    }

}
