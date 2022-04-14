package app.controllers.rest;

import app.entities.Passenger;
import app.entities.dtos.PassengerDTO;
import app.entities.mappers.passenger.PassengerMapper;
import app.services.interfaces.PassengerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Rest контроллер для работы с пассажирами
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "PassengerRestController")
@RequestMapping("/api/passenger")
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
    public ResponseEntity<PassengerDTO> getPassengerById(
            @ApiParam(value = "ID Пассажира", example = "1") @PathVariable Long id) {
        Optional<Passenger> passenger = passengerService.findById(id);

        if (passenger.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(passengerMapper.toDto(passenger.get()), HttpStatus.OK);
        }
    }


    /**
     * Метод получает всех пассажиров
     *
     */
    @ApiOperation(value = "Запрос для получения всех пассажиров")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получены"),
            @ApiResponse(code = 404, message = "Пассажир не найден")
    })
    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
        List<Passenger> passengersList = passengerService.findAll();
        if (passengersList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<PassengerDTO> passengersDTOList = passengersList.stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(passengersDTOList, HttpStatus.OK);
    }


    /**
     * Удаление записи о пассажире из таблицы БД по переданному id.
     *
     * @param id - уникальный идентификатор пассажира, по которому ищется запись из таблицы в БД.
     * @return void
     */
    @ApiOperation(value = "Запрос для удаления записи об пассажире из таблицы в БД",
            notes = "Удаление записи об пассажире")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Пассажир успешно удалён"),
            @ApiResponse(code = 404, message = "Запись о пассажире не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassengerById(@ApiParam(example = "1", value = "ID администратора")
                                                    @PathVariable long id) {
        Optional<Passenger> passenger = passengerService.findById(id);
        if (passenger.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        passengerService.deletePassengerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
