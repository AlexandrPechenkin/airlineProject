package app.controllers.rest;

import app.entities.Admin;
import app.entities.dtos.AdminDTO;
import app.entities.Passenger;
import app.entities.dtos.PassengerDTO;
import app.entities.mappers.user.UserMapper;

import app.services.interfaces.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * RestController для управления записями о классах типа User.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "UserRestController")
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation(value = "Запрос для создания пассажира", notes = "Создание пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping("/passenger")
    public ResponseEntity<PassengerDTO> createPassengerUser(@ApiParam(value = "DTO пользователя") @RequestBody @Valid PassengerDTO userDTO) {
        if (Objects.nonNull(userDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<PassengerDTO>(
                userMapper.toPassengerDto(
                        (Passenger) userService.createOrUpdateUser(
                                userMapper.toPassengerEntity(userDTO))), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Запрос для создания пассажира", notes = "Создание пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping("/admin")
    public ResponseEntity<AdminDTO> createAdminUser(@ApiParam(value = "DTO администратора") @RequestBody @Valid AdminDTO userDTO) {
        if (Objects.nonNull(userDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<AdminDTO>(
                userMapper.toAdminDto(
                        (Admin) userService.createOrUpdateUser(
                                userMapper.toAdminEntity(userDTO))), HttpStatus.CREATED);
    }



}
