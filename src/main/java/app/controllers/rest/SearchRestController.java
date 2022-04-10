package app.controllers.rest;

import app.entities.Route;
import app.entities.Search;
import app.entities.SearchResult;
import app.entities.dtos.SearchResultDTO;
import app.entities.mappers.flight.FlightListMapper;
import app.entities.mappers.searchResult.SearchResultMapper;
import app.exception.NoSuchObjectException;
import app.services.interfaces.DestinationResourceService;
import app.services.interfaces.SearchService;
import app.util.JsonParser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@Api(tags = "SearchRestController")
@RequestMapping("/api/search")
@Validated
public class SearchRestController {
    private final SearchService searchService;
    private final JsonParser jsonParser;
    private final DestinationResourceService destinationResourceService;
    private final FlightListMapper flightListMapper;
    private final SearchResultMapper searchResultMapper;
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
     * Метод для запроса всех поисков.
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
     * Поиск перелета по JSON Route месту вылета, месту прилета и дате вылета.
     * Получаем JSON Route с фронта, парсим в объект Route, ищем по from, to, departureDate все перелеты.
     * @return {@link ResponseEntity<SearchResultDTO>}
     */
    @ApiOperation(value = "Запрос для получения перелета по месту вылета, месту прилета и дате вылета",
            notes = "Возвращение перелета по from, to, departureDate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Перелет не найден")
    })
    @PostMapping("/")
    public ResponseEntity<SearchResultDTO> searchFlightsByRouteFromJSON(@RequestBody @Valid String jsonRoute)
            throws ParseException {
        Route route = jsonParser.getFlightPropertiesByJSONWithCityNames(jsonRoute);
        return new ResponseEntity<>(searchResultMapper
                .toDto(searchService.getSearchResultByCitiesAndLocalDates(
                                route.getFrom().getCity(), route.getTo().getCity(),
                                route.getDepartureDate(), route.getReturnDate())), HttpStatus.OK);
    }

    /**
     * Поиск перелёта по переданным местам вылета, прилёта и датам вылета и (если указано) дате возвращения.
     * @return {@link ResponseEntity<SearchResultDTO>}
     */
    @ApiOperation(value = "Запрос для получения перелета по месту вылета, месту прилета и дате вылета из url",
            notes = "Возвращение перелета по from, to, departureDate из url")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешно получен"),
            @ApiResponse(code = 404, message = "Перелет не найден")
    })
    @PostMapping("/{from}/{to}")
    public ResponseEntity<SearchResultDTO>
    getSearchResultByCities(@PathVariable("from") String from, @PathVariable("to") String to,
                            @RequestParam(value = "departDate")
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                            @RequestParam(value = "returnDate", required = false)
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) throws ParseException {
        SearchResult searchResult = searchService.getSearchResultByCitiesAndLocalDates(from, to, departureDate, returnDate);
        return new ResponseEntity<>(searchResultMapper.toDto(searchResult), HttpStatus.OK);
    }
}
