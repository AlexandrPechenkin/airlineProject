package app.controllers.rest;

import app.entities.Registration;
import app.entities.dtos.RegistrationDTO;
import app.entities.mappers.registration.RegistrationMapper;
import app.services.interfaces.RegistrationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
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
@Api(tags = "RegistrationRestController")
@RequestMapping("/registration")
@Validated
public class RegistrationRestController {
    private final RegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    /**
     * Создание записи о регистрировании пассажира на рейс.
     *
     * @param registration - данные новой записи о регистрации.
     * @return {@link ResponseEntity<RegistrationDTO>}
     */
    @ApiOperation(value = "Запрос для создания регистрации пассажира на рейс",
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
                        registrationService.createOrUpdateRegistration(
                                registrationMapper.toEntity(registration))), HttpStatus.CREATED);
    }

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
