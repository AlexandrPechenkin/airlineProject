package app.controllers.flight;

import app.entities.flight.Flight;
import app.services.flight.FlightService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api(tags = "FlightController")
@RequestMapping("/flight")
public class FlightRestController {
    private final FlightService flightService;

    public FlightRestController(FlightService flightService) {
        this.flightService = flightService;
    }


    /**
     * Создание нового перелета
     *
     * @param flight
     * @return
     */
    @PostMapping("/")
    public ResponseEntity addFlight(@RequestBody Flight flight) {
        flightService.createOrUpdateFlight(flight);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * обновление нового перелета по id
     *
     * @param id
     * @param flight
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateFlight(@PathVariable("id") Long id, @RequestBody Flight flight) {
        flightService.createOrUpdateFlight(flight);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * поиск перелета по месту вылета, месту прилета и дате вылета
     *
     * @param from          место вылета
     * @param to            место прилета
     * @param departureDate дата вылета
     * @return
     */
    @GetMapping("/{from}/{to}/{departureDate}")
    public ResponseEntity<List<Flight>> searchFlights(@PathVariable("from") String from, @PathVariable("to") String to,
                                                      @PathVariable("departureDate") LocalDate departureDate) {
        return ResponseEntity.ok(flightService.findFlights(from, to, departureDate));
    }

    /**
     * получение перелета по id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Flight> searchFlightById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }
}
