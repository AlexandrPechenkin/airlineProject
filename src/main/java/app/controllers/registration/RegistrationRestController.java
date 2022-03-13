package app.controllers.registration;

import app.entities.registration.Registration;
import app.entities.registration.dto.RegistrationDTO;
import app.mappers.registration.RegistrationMapper;
import app.services.registration.RegistrationService;
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
@Api(tags = "RegistrationRestController")
@RequestMapping("/registration")
@Validated
public class RegistrationRestController {
    private final RegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    /**
     * Создание регистрирования
     *
     * @param registration
     * @return
     */
    @ApiOperation(value = "Запрос для создания регистрации пассажира на рейс",
                  notes = "Создание регистрации пассажира на рейс")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Регистрирование рейса успешно завершено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<RegistrationDTO> createOrUpdate(@ApiParam(value = "Registration DTO")
                                                    @RequestBody @Valid RegistrationDTO registration) {
        if (Objects.nonNull(registration.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>
                (registrationMapper.toDto(registrationService.createOrUpdate(registrationMapper.toEntity(registration))),
                        HttpStatus.CREATED);
    }

    /**
     * Найти регистрацию пассажира по id.
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Запрос для получения регистрации на рейс пассажира по id регистрации",
            notes = "Получение регистрации на рейс пассажира по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Регистрирование на рейс пассажира не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getById(@ApiParam(value = "id регистрирования на рейс пассажира")
                                                              @PathVariable Long id) {
        Optional<Registration> registration = registrationService.findById(id);

        if (registration.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrationMapper.toDto(registration.get()), HttpStatus.OK);
    }

    /**
     * Удаление регистрации.
     *
     * @param registration
     * @return
     */
    @ApiOperation(value = "Запрос на удаление регистрации пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Регистрация пассажира успешно удалена")
    })
    @DeleteMapping()
    public ResponseEntity delete(Registration registration) {
        registrationService.delete(registration);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
