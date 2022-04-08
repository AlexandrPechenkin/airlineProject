package app.controllers.rest;

import app.entities.DestinationResource;
import app.entities.Flight;
import app.entities.Route;
import app.entities.Search;
import app.entities.dtos.FlightDTO;
import app.exception.NoSuchObjectException;
import app.services.interfaces.DestinationResourceService;
import app.services.interfaces.SearchService;
import app.util.JsonParser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@Api(tags = "SearchController")
@RequestMapping("/api/search")
@Validated
public class SearchRestController {
    private final SearchService searchService;
    private final JsonParser jsonParser;
    private final DestinationResourceService destinationResourceService;

    /**
     * метод для запроса поиска по id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Запрос для получения поиска по id", notes = "Возвращение поиска по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Поиск не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Search> getSearchById(@ApiParam(value = "Id поиска", example = "1")
                                                @PathVariable("id") Long id) {
        Optional<Search> search = searchService.getById(id);
        if (search.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(searchService.getById(id), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }

    /**
     * метод для запроса всех поисков
     *
     * @return
     */
    @ApiOperation(value = "Запрос для получение всех поисков", notes = "Возвращение всех поисков")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Поиски не найдены")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Search>> getAllSearches() {
        List<Search> searches = searchService.getAll();
        if (searches.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(searchService.getAll(), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }


    /**
     * поиск перелета по JSON Route месту вылета, месту прилета и дате вылета
     * Получаем JSON Route с фронта, парсим в объект Route, ищем по from, to, departureDate все перелеты
     * @return
     */
    @ApiOperation(value = "Запрос для получения перелета по месту вылета, месту прилета и дате вылета",
            notes = "Возвращение перелета по from, to, departureDate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Перелет не найден")
    })
    @PostMapping("/")
    public ResponseEntity<List<FlightDTO>> searchFlightsByRoute(@RequestBody @Valid String jsonRoute) throws ParseException {
        Route route = jsonParser.getFlightPropertiesByJSONWithCityNames(jsonRoute);
        Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> flightList = searchService.getFlights(
                searchService.getRoutes(
                        destinationResourceService.findByCity(route.getFrom().getCity()),
                        destinationResourceService.findByCity(route.getTo().getCity()),
                        route.getDepartureDate()),
                route.getDepartureDate());
        if (flightList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity(flightList, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new NoSuchObjectException("Error");
        }
    }
}
