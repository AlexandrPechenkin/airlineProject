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
    private RegistrationService registrationService;
    private RegistrationMapper registrationMapper;

    @ApiOperation(value = "Запрос для создания регистрации пассажира на рейс",
                  notes = "Создание регистрации пассажира на рейс")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Регистрирование рейса успешно завершено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<RegistrationDTO> createBooking(@ApiParam(value = "Registration DTO")
                                                    @RequestBody @Valid RegistrationDTO registration) {
        if (Objects.nonNull(registration.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>
                (registrationMapper.toDto(registrationService.createOrUpdateRegistration(registrationMapper.toEntity(registration))),
                        HttpStatus.CREATED);
    }

    @ApiOperation(value = "Запрос для получения регистрации на рейс пассажира по id регистрации",
            notes = "Получение регистрации на рейс пассажира по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Регистрирование на рейс пассажира не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getBookingById(@ApiParam(value = "id регистрирования на рейс пассажира")
                                                              @PathVariable Long id) {
        Optional<Registration> booking = registrationService.findById(id);

        if (booking.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrationMapper.toDto(booking.get()), HttpStatus.OK);
    }
}
