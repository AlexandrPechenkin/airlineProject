package app.controllers.rest;

import app.entities.Search;
import app.exception.NoSuchObjectException;
import app.services.interfaces.SearchService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@Api(tags = "SearchController")
@RequestMapping("api/search")
@Validated
public class SearchRestController {
    private final SearchService searchService;

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
}
