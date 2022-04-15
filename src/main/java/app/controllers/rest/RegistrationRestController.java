package app.controllers.rest;

import app.entities.Registration;
import app.entities.dtos.RegistrationDTO;
import app.entities.mappers.registration.RegistrationMapper;
import app.services.interfaces.RegistrationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * RestController для управления записями о классе {@link Registration} в БД.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "RegistrationRestController")
@RequestMapping("/api/registration")
@Validated
public class RegistrationRestController {
    private final RegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    /*@ApiOperation(value = "Запрос для создания регистрации пассажира на рейс",
            notes = "Создание регистрации пассажира на рейс")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Регистрирование рейса успешно завершено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<RegistrationDTO> createRegistration(@ApiParam(value = "Registration DTO")
                                                              @RequestBody @Valid RegistrationDTO registration) {
        return new ResponseEntity<>
                (registrationMapper.toDto(
                        registrationService.createOrUpdateOrDeleteRegistration(
                                registrationMapper.toEntity(registration))), HttpStatus.CREATED);
    }*/

    /**
     *
     * @param holdNumber -номер брони
     * @param seatId - идентификатор места
     * @return {@link Void}
     */
    @ApiOperation(value = "Запрос для создания регистрации пассажира на рейс",
            notes = "Создание регистрации пассажира на рейс")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Регистрирование рейса успешно завершено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping("/{holdNumber}/{seatId}")
    public ResponseEntity<Void>
        createRegistrationByHoldNumberAndSeatId(@ApiParam(value = "Номер брони")
                                                @PathVariable("holdNumber") Long holdNumber,
                                                @ApiParam(value = "Id места")
                                                @PathVariable("seatId") Long seatId) {
        Registration registration = registrationService.createRegistrationByHoldNumberAndSeatId(
                holdNumber, seatId);

        if (registration == null) {
            log.error("Ошибка при создании объекта регистрации");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Запрос на получение регистрации по номеру бронирования",
        notes = "Получение регистрации по holdNumber")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Регистрация не найдена")
    })
    @GetMapping("/holdNumber/{holdNumber}")
    public ResponseEntity<String> getRegistrationByHoldNumber(@ApiParam(value = "Номер бронирования")
                                                                       @PathVariable("holdNumber") Long holdNumber) {
        String registrationStatus = registrationService.getRegistrationStatusByHoldNumber(holdNumber);
        if (registrationStatus == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(registrationStatus, HttpStatus.OK);
        }
    }

     ///**
     //* Обновление записи о регистрировании пассажира на рейс.
     //*
     //* @param registration - данные для обновления о регистрации.
     //* @return {@link ResponseEntity<RegistrationDTO>}
     //*/
    /*@ApiOperation(value = "Запрос для обновления регистрации пассажира на рейс",
            notes = "Обновление регистрации пассажира на рейс")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Обновление данных о регистрации на рейс успешно завершено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PutMapping
    public ResponseEntity<RegistrationDTO> updateRegistration(@ApiParam(value = "Registration DTO")
                                                              @RequestBody @Valid RegistrationDTO registration) {
        return new ResponseEntity<>
                (registrationMapper.toDto(
                        registrationService.createRegistrationByHoldNumberAndSeatId(
                                registrationMapper.toEntity(registration))), HttpStatus.OK);
    }*/

    /**
     * Найти регистрацию пассажира по id.
     *
     * @param id - уникальный идентификатор записи о регистрации в БД.
     * @return {@link ResponseEntity<RegistrationDTO>}
     */
    @ApiOperation(value = "Запрос для получения регистрации на рейс пассажира по id регистрации",
            notes = "Получение регистрации на рейс пассажира по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Регистрирование на рейс пассажира не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@ApiParam(value = "id регистрирования на рейс пассажира")
                                                   @PathVariable long id) {
        Optional<Registration> registration = registrationService.findById(id);

        if (registration.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrationMapper.toDto(registration.get()), HttpStatus.OK);
    }

    /**
     * Удаление записи о регистрации пассажира из БД.
     *
     * @param id - уникальный идентификатор записи регистрации в БД.
     * @return {@link ResponseEntity<Void>}
     */
    @ApiOperation(value = "Запрос на удаление регистрации пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Регистрация пассажира успешно удалена"),
            @ApiResponse(code = 404, message = "Запись о регистрации не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistrationById(@ApiParam(example = "1", value = "ID регистрации")
                                       @PathVariable long id) {
        Optional<Registration> registration = registrationService.findById(id);
        if (registration.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        registrationService.deleteRegistrationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
