package app.controllers.flight;

import app.entities.flight.Flight;
import app.entities.flight.dto.FlightDTO;
import app.exception.NoSuchObjectException;
import app.exception.ObjectIncorrectData;
import app.mappers.flight.FlightMapper;
import app.services.flight.FlightService;
import io.swagger.annotations.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    public ResponseEntity<FlightDTO> createFlight(@ApiParam(value = "DTO вылета") @Valid @RequestBody FlightDTO flight) {
        try {
            return new ResponseEntity<>(flightMapper.toDTO(flightService.createOrUpdateFlight(flightMapper.toEntity(flight))), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * обновление нового перелета по id
     *
     * @param id
     * @param flight
     * @return
     */
    @ApiOperation(value = "Запрос для обновления перелета", notes = "Обновление перелета")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Перелет успешно обновлен"),
            @ApiResponse(code = 400, message = "Перелет неверные данные"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable("id") Long id,
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
    public ResponseEntity<List<Flight>> searchFlights(@PathVariable("from") String from,
                                                      @PathVariable("to") String to,
                                                      @PathVariable("departureDate") LocalDate departureDate) {
        return new ResponseEntity<>(flightService.findFlights(from, to, departureDate), HttpStatus.OK);
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
    public ResponseEntity<Flight> searchFlightById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(flightService.getFlightById(id), HttpStatus.OK);
    }
}
