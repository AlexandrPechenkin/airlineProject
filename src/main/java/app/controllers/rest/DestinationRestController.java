package app.controllers.rest;

import app.entities.Destination;
import app.entities.dtos.DestinationDTO;
import app.entities.mappers.destination.DestinationListMapper;
import app.entities.mappers.destination.DestinationMapper;
import app.services.interfaces.DestinationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Rest контроллер для аэропорта
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "DestinationController")
@RequestMapping("/api/v1/destinations")
public class DestinationRestController {
    /**
     * Интерфейс сервиса для работы с аэропортом
     */
    private final DestinationService destinationService;
    /**
     * Маппер для аэропорта
     */
    private final DestinationMapper destinationMapper;
    /**
     * Маппер для списка аэропортов
     */
    private final DestinationListMapper destinationListMapper;

    /**
     * Возвращает DTO аэропорта по ID
     * @param id - ID аэропорта
     * @return - DTO аэропорта
     */
    @ApiOperation(value = "Запрос для получения аэропорта по ID",
            notes = "Возвращение аэропорта по ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аэропорт успешно получен"),
            @ApiResponse(code = 404, message = "Аэропорт не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DestinationDTO> getDestinationById(
            @ApiParam(value = "ID аэропорта") @PathVariable Long id) {

        Optional<Destination> destination = destinationService.getDestinationById(id);

        if (destination.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(destinationMapper.toDTO(destination.get()), HttpStatus.OK);
        }
    }

    /**
     * Возвращает список аэропортов по названию города
     * @param city - название города, в котором находятся аэропорты
     * @return - список аэропортов
     */
    @ApiOperation(value = "Запрос для получения списка аэропортов по названию города",
            notes = "Возвращение списка аэропортов по названию города")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список аэропортов успешно получен"),
            @ApiResponse(code = 404, message = "Аэропорты не найдены")
    })
    @GetMapping("/city/{city}")
    public ResponseEntity<List<DestinationDTO>> getDestinationListByCity(
            @ApiParam(value = "Название города") @PathVariable String city) {

        List<Destination> destinationList = destinationService.getDestinationListByCity(city);

        if (destinationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(destinationListMapper.toDTOList(destinationList), HttpStatus.OK);
        }
    }

    /**
     * Возвращает список аэропортов по названию страны
     * @param country - название страны, в которой находятся аэропорты
     * @return - список аэропортов
     */
    @ApiOperation(value = "Запрос для получения списка аэропортов по названию страны",
            notes = "Возвращение списка аэропортов по названию страны")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список аэропортов успешно получен"),
            @ApiResponse(code = 404, message = "Аэропорты не найдены")
    })
    @GetMapping("/country/{country}")
    public ResponseEntity<List<DestinationDTO>> getDestinationListByCountryName(
            @ApiParam(value = "Название страны") @PathVariable String country) {

        List<Destination> destinationList = destinationService.getDestinationListByCountryName(country);

        if (destinationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(destinationListMapper.toDTOList(destinationList), HttpStatus.OK);
        }
    }

    /**
     * Создает аэропорт
     * @param destinationDTO - DTO аэропорта
     * @return - DTO аэропорта
     */
    @ApiOperation(value = "Запрос для создания аэропорта", notes = "Создание аэропорта")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Аэропорт успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<DestinationDTO> createDestination(
            @ApiParam(value = "DTO аэропорта") @RequestBody @Valid DestinationDTO destinationDTO) {

        if (Objects.nonNull(destinationDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Destination destination = destinationMapper.toEntity(destinationDTO);
        destinationService.createOrUpdateDestination(destination);
        return new ResponseEntity<>(destinationDTO, HttpStatus.CREATED);
    }

    /**
     * Обновляет аэропорт
     * @param destinationDTO - DTO аэропорта
     * @return - DTO аэропорта
     */
    @ApiOperation(value = "Запрос для обновления аэропорта", notes = "Обновление аэропорта")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аэропорт успешно обновлен"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PutMapping
    public ResponseEntity<DestinationDTO> updateDestinationById(
            @ApiParam(value = "DTO аэропорта") @RequestBody @Valid DestinationDTO destinationDTO) {

        Destination destination = destinationMapper.toEntity(destinationDTO);
        destinationService.createOrUpdateDestination(destination);
        return new ResponseEntity<>(destinationDTO, HttpStatus.OK);
    }
}
