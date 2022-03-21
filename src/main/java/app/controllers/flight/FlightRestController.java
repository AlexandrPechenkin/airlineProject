package app.controllers.flight;

import app.entities.flight.Flight;
import app.entities.flight.dto.FlightDTO;
import app.exception.NoSuchObjectException;
import app.mappers.flight.FlightMapper;
import app.services.flight.FlightService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@Api(tags = "FlightController")
@RequestMapping("api/flight")
@Validated
public class FlightRestController {
    private final FlightService flightService;
    private final FlightMapper flightMapper;


    /**
     * Создание нового перелета
     *
     * @param flight
     * @return
     */
    @ApiOperation(value = "Запрос для создания перелета", notes = "Создание перелета")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Перелет успешно создан"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@ApiParam(value = "DTO вылета")
                                                  @Valid @RequestBody FlightDTO flight) {
        if (Objects.isNull(flight.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(flightMapper.toDTO(flightService.createOrUpdateFlight(flightMapper.toEntity(flight))), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * обновление нового перелета по id
     *
     * @param flight
     * @return
     */
    @ApiOperation(value = "Запрос для обновления перелета", notes = "Обновление перелета")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Перелет успешно обновлен"),
            @ApiResponse(code = 400, message = "Перелет неверные данные"),
    })
    @PutMapping
    public ResponseEntity<FlightDTO> updateFlight(@ApiParam(value = "DTO перелета")
                                                  @Valid @RequestBody FlightDTO flight) {
        try {
            return new ResponseEntity<>(flightMapper.toDTO(flightService.createOrUpdateFlight(flightMapper.toEntity(flight))), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * поиск перелета по месту вылета, месту прилета и дате вылета
     *
     * @param from          место вылета
     * @param to            место прилета
     * @param departureDate дата вылета
     * @return
     */
    @ApiOperation(value = "Запрос для получения перелета по месту вылета, месту прилета и дате вылета",
            notes = "Возвращение перелета по from, to, departureDate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Перелет не найден")
    })
    @GetMapping("/{from}/{to}/{departureDate}")
    public ResponseEntity<List<FlightDTO>> searchFlights(@ApiParam(value = "место вылета")
                                                         @PathVariable("from") String from,
                                                         @ApiParam(value = "место прилета")
                                                         @PathVariable("to") String to,
                                                         @ApiParam(value = "дата вылета")
                                                         @PathVariable("departureDate") LocalDate departureDate) {
        List<Flight> flightList = flightService.findFlights(from, to, departureDate);
        if (flightList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(flightList, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * получение перелета по id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Запрос для получения перелета по id", notes = "Возвращение перелета по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Перелет не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Flight> searchFlightById(@ApiParam(value = "Id перелета", example = "1")
                                                   @PathVariable("id") Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        if (flight.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(flightService.getFlightById(id), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }
}
