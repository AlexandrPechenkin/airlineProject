package app.controllers.rest;


import app.entities.Aircraft;
import app.entities.dtos.AircraftDTO;
import app.entities.mappers.aircraft.AircraftMapper;
import app.services.interfaces.AircraftService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

/**
 * Rest контроллер для работы с воздушным судном
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = "AircraftController")
@RequestMapping("/api/aircraft")
public class AircraftRestController {
    private final AircraftService aircraftService;
    private final AircraftMapper aircraftMapper;

    @ApiOperation(value = "Запрос для создания воздушного судна", notes = "Создание воздушного судна")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Воздушное судно успешно создано"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<AircraftDTO> createAircraft(@ApiParam(value = "DTO Воздушного судна") @RequestBody @Valid AircraftDTO aircraft) {
        if (Objects.isNull(aircraft.getId())) {
            log.error("Произошла попытка создать (воздушное судно) через метод создания.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(
                    aircraftMapper.toDto(
                            aircraftService.createOrUpdateAircraft(
                                    aircraftMapper.toEntity(aircraft))), HttpStatus.CREATED);
        }
    }

    @ApiOperation(value = "Запрос для обновления воздушного судна", notes = "Обновление воздушного судна")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Воздушное судно успешно обновлено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PutMapping
    public ResponseEntity<AircraftDTO> updateAircraft(@ApiParam(value = "DTO Воздушного судна") @RequestBody @Valid AircraftDTO aircraft) {
        if (Objects.nonNull(aircraft.getId())) {
            return new ResponseEntity<>(
                    aircraftMapper.toDto(
                            aircraftService.createOrUpdateAircraft(
                                    aircraftMapper.toEntity(aircraft))), HttpStatus.OK);
        } else {
            log.error("Произошла попытка обновить (воздушное судно) через метод обновления.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Запрос для получения воздушного судна по ID", notes = "Получение воздушного судна по ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получено"),
            @ApiResponse(code = 404, message = "Воздушное судно не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AircraftDTO> getAircraftById(@ApiParam(value = "ID Воздушного судна", example = "1") @PathVariable Long id) {
        Optional<Aircraft> aircraft = aircraftService.getAircraftById(id);
        if (aircraft.isEmpty()) {
            log.error("Произошла попытка получения (воздушного судно) по ID, но такого ID не оказалось в базе данных.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(aircraftMapper.toDto(aircraft.get()), HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Запрос для удаления воздушного судна по ID", notes = "Удаление воздушного судна по ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно удалено"),
            @ApiResponse(code = 404, message = "Воздушное судно не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAircraftById(@ApiParam(value = "ID Воздушного судна", example = "1") @PathVariable Long id) {
        Optional<Aircraft> aircraft = aircraftService.getAircraftById(id);
        if (aircraft.isEmpty()) {
            log.error("Произошла попытка удаление (воздушного судно) по ID, но такого ID не оказалось в базе данных.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            aircraftService.deleteAircraftById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
