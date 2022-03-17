package app.controllers.user;

import app.entities.users.admin.dto.AdminDTO;
import app.entities.users.passenger.dto.PassengerDTO;
import app.entities.users.user.dto.UserDTO;
import app.mappers.users.admin.AdminMapper;
import app.mappers.users.airlineManager.AirlineManagerMapper;
import app.mappers.users.passenger.PassengerMapper;
import app.mappers.users.user.UserMapper;
import app.services.interfaces.UserService;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = "UserRestController")
@RequestMapping("/user/")
public class UserRestController {
    private final UserService userService;
    UserMapper userMapper;
//    PassengerMapper passengerMapper;
//    @Qualifier("AirlineManagerMapperImpl") AirlineManagerMapper airlineManagerMapper;
//    @Qualifier("AdminMapperImpl") AdminMapper adminMapper;

    @ApiOperation(value = "Запрос для создания пассажира", notes = "Создание пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping("/passenger/")
    public ResponseEntity<UserDTO> createPassengerUser(@ApiParam(value = "DTO пользователя") @RequestBody @Valid PassengerDTO userDTO) {
        if (Objects.nonNull(userDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                userMapper.toDto(
                        userService.createOrUpdateUser(
                                userMapper.toEntity(userDTO))), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Запрос для создания пассажира", notes = "Создание пассажира")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping("/admin/")
    public ResponseEntity<UserDTO> createAdminUser(@ApiParam(value = "DTO пользователя") @RequestBody @Valid AdminDTO userDTO) {
        if (Objects.nonNull(userDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                userMapper.toDto(
                        userService.createOrUpdateUser(
                                userMapper.toEntity(userDTO))), HttpStatus.CREATED);
    }



}
