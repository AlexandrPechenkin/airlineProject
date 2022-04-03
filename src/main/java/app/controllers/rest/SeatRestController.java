package app.controllers.rest;


import app.entities.dtos.SeatDTO;
import app.entities.mappers.seat.SeatMapper;
import app.services.interfaces.FlightService;
import app.services.interfaces.SeatService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Рест контроллер для работы с местами
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@Api(tags = "SeatRestController")
@RequestMapping("/api/seat")
public class SeatRestController {

    private final SeatService seatService;
    private final SeatMapper seatMapper;
    private final FlightService flightService;

//    @ApiOperation(value = "Запрос для получения всех мест конкретного рейса")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Места успешно получены"),
//            @ApiResponse(code = 400, message = "Переданы неверные данные")
//    })
//    @GetMapping("/{id}")
//    public ResponseEntity<List<Seat>> getAllSeat(@ApiParam(example = "1") @PathVariable long id) {
//        List<Seat> seat = seatService.getAllSeatByFlightId(id);
//
//        if (seat.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(seat, HttpStatus.OK);
//        }
//    }
//
//    @ApiOperation(value = "Запрос для получения мест конкретного рейса определенной категории")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Места успешно получены"),
//            @ApiResponse(code = 400, message = "Переданы неверные данные")
//    })
//    @GetMapping("/{flight}/{category}")
//    public ResponseEntity<List<Seat>> getSeatByFlightAndCategory(
//            @ApiParam(example = "1") @PathVariable long flight, @ApiParam(example = "1") @PathVariable long category) {
//
//        List<Seat> seat = seatService.getSeatByFlightAndCategory(flight, category);
//
//        if (seat.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(seat, HttpStatus.OK);
//        }
//    }

    @ApiOperation(value = "Запрос для создания места на рейсе")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Место успешно создано"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping()
    public ResponseEntity<SeatDTO> createSeat(@ApiParam(value = "DTO места")
                                              @RequestBody @Valid SeatDTO seat) {
        return new ResponseEntity<>(
                    seatMapper.toDto(
                            seatService.createOrUpdate(
                                    seatMapper.toEntity(seat))), HttpStatus.CREATED);

    }


    @ApiOperation(value = "Обновление места")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Место успешно обновлено"),
            @ApiResponse(code = 400, message = "Переданы неверные данные"),
    })
    @PutMapping()
    public ResponseEntity<SeatDTO> updateSeat(@ApiParam(value = "DTO места") @RequestBody @Valid SeatDTO seat) {

        return new ResponseEntity<>(
                seatMapper.toDto(
                        seatService.createOrUpdate(
                                seatMapper.toEntity(seat))), HttpStatus.OK);
    }


//    @ApiOperation(value = "Запрос для получения количества непроданных мест")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Места успешно получены"),
//            @ApiResponse(code = 400, message = "Переданы неверные данные")
//    })
//    @GetMapping("/no-sold/{id}")
//    public ResponseEntity<Long> getCountNoSoldSeat(@ApiParam(example = "1") @PathVariable long id) {
//        Optional<Flight> flight = flightService.findById(id);
//
//        if (flight.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(seatService.getCountNoSoldSeat(id), HttpStatus.OK);
//        }
//    }
//
//
//    @ApiOperation(value = "Запрос для получения количества проданных мест")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Места успешно получены"),
//            @ApiResponse(code = 400, message = "Переданы неверные данные")
//    })
//    @GetMapping("/sold/{id}")
//    public ResponseEntity<Long> getCountSoldSeat(@ApiParam(example = "1") @PathVariable long id) {
//        Optional<Flight> flight = flightService.findById(id);
//
//        if (flight.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(seatService.getCountSoldSeat(id), HttpStatus.OK);
//        }
//    }
//
//
//    @ApiOperation(value = "Запрос для получения количества зарегистрированных пассажиров")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Зарегистрированные пассажиры успешно получены"),
//            @ApiResponse(code = 400, message = "Переданы неверные данные")
//    })
//    @GetMapping("/registered/{id}")
//    public ResponseEntity<Long> getCountRegisteredPassenger(@ApiParam(example = "1") @PathVariable long id) {
//        Optional<Flight> flight = flightService.findById(id);
//
//        if (flight.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(seatService.getCountRegisteredPassenger(id), HttpStatus.OK);
//        }
//    }

}
