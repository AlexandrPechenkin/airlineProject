package app.controllers.rest;

import app.entities.AirlineManager;
import app.entities.dtos.AirlineManagerDTO;
import app.entities.mappers.airlineManager.AirlineManagerMapper;
import app.services.interfaces.AirlineManagerService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * RestController для управления записями о классе {@link AirlineManager} в БД.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "AirlineManagerRestController")
@RequestMapping("/airlineManager")
public class AirlineManagerRestController {
    private final AirlineManagerMapper airlineManagerMapper;
    private final AirlineManagerService airlineManagerService;

    /**
     * Получение всех записей о менеджерах из БД.
     *
     * @return List of {@link AirlineManagerDTO} - список всех записей о менеджерах в БД.
     */
    @ApiOperation(value = "Запрос для получения всех записей о менеджерах",
            notes = "Получение всех записей о менеджерах")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Записи о менеджерах успешно получены"),
            @ApiResponse(code = 404, message = "Записи о менеджерах не найдены")
    })
    @GetMapping
    public ResponseEntity<List<AirlineManagerDTO>> findAllAirlineManager() {
        List<AirlineManager> airlineManagerList = airlineManagerService.findAll();
        if (airlineManagerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AirlineManagerDTO> airlineManagerDTOList = airlineManagerList.stream()
                .map(airlineManagerMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(airlineManagerDTOList, HttpStatus.OK);
    }

    /**
     * Создание записи о менеджере в БД и получение записи в теле ответа.
     *
     * @param airlineManagerDTO - данные о новом менеджере, переданные с фронтенда.
     * @return {@link AirlineManagerDTO}
     */
    @ApiOperation(value = "Запрос для создания менеджера", notes = "Создание менеджера")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Менеджер успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<AirlineManagerDTO> createAirlineManager(@ApiParam(value = "DTO менеджера") @RequestBody
                                                                      @Valid AirlineManagerDTO airlineManagerDTO) {
        return new ResponseEntity<>(
                airlineManagerMapper.toDto(
                        airlineManagerService.createOrUpdateAirlineManager(
                                airlineManagerMapper.toEntity(airlineManagerDTO))), HttpStatus.CREATED);
    }

    /**
     * Обновление данных о менеджере в БД и получение записи об обновлённом менеджере из БД в теле ответа.
     *
     * @param airlineManagerDTO - обновлённые данные о существующем менеджере, переданные с фронтенда.
     * @return {@link AirlineManagerDTO}
     */
    @ApiOperation(value = "Запрос для обновления данных менеджера", notes = "Обновление менеджера")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Менеджер успешно обновлён"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PutMapping
    public ResponseEntity<AirlineManagerDTO> updateAirlineManager(@ApiParam(value = "DTO менеджера") @RequestBody
                                                                      @Valid AirlineManagerDTO airlineManagerDTO) {
        return new ResponseEntity<>(
                airlineManagerMapper.toDto(
                        airlineManagerService.createOrUpdateAirlineManager(
                                airlineManagerMapper.toEntity(airlineManagerDTO))), HttpStatus.OK);
    }

    /**
     * Получение записи о менеджера из таблицы БД по переданному id.
     *
     * @param id - уникальный идентификатор менеджера, по которому ищется запись из таблицы в БД.
     * @return {@link AirlineManagerDTO}
     */
    @ApiOperation(value = "Запрос для получения записи об менеджере по id", notes = "Получение менеджера по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Менеджер успешно получен"),
            @ApiResponse(code = 404, message = "Запись о менеджере не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AirlineManagerDTO> getAirlineManagerById(@ApiParam(example = "1", value = "ID менеджера")
                                                                       @PathVariable long id) {
        Optional<AirlineManager> airlineManager = airlineManagerService.findById(id);
        if (airlineManager.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(airlineManagerMapper.toDto(airlineManager.get()), HttpStatus.OK);
    }

    /**
     * Удаление записи о менеджере из таблицы БД по переданному id.
     *
     * @param id - уникальный идентификатор менеджера, по которому ищется запись из таблицы в БД.
     * @return void
     */
    @ApiOperation(value = "Запрос для удаления записи об менеджере из таблицы в БД",
            notes = "Удаление записи о менеджере")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Менеджер успешно удалён"),
            @ApiResponse(code = 404, message = "Запись о менеджере не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirlineManagerById(@ApiParam(example = "1", value = "ID менеджера")
                                                             @PathVariable long id) {
        Optional<AirlineManager> airlineManager = airlineManagerService.findById(id);
        if (airlineManager.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        airlineManagerService.deleteAirlineManagerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
