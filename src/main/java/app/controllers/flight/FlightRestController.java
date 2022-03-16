//package app.controllers.flight;
//
//
//import app.entities.flight.dto.FlightDTO;
//import app.mappers.flight.FlightMapper;
//import app.services.interfaces.FlightService;
//import io.swagger.annotations.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@Api(tags = "FlightController")
//@RequestMapping("/flight")
//public class FlightRestController {
//
//    private FlightService flightService;
//    private FlightMapper flightMapper;
//
//
//    @ApiOperation(value = "Запрос для создания рейса")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Рейс успешно создан"),
//            @ApiResponse(code = 400, message = "Переданы неверные данные")
//    })
//    @PostMapping()
//    public ResponseEntity<FlightDTO> createFlight(@ApiParam(value = "DTO рейса")
//                                              @RequestBody FlightDTO flightDTO) {
//        return new ResponseEntity<>(
//                flightMapper.toDto(
//                        flightService.addFlight(
//                                flightMapper.toEntity(flightDTO))), HttpStatus.CREATED);
//    }
//}
