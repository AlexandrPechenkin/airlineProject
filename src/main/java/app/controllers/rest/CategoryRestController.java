package app.controllers.rest;

import app.entities.Category;
import app.entities.dtos.CategoryDTO;
import app.entities.mappers.category.CategoryMapper;
import app.services.interfaces.CategoryService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * Рест контроллер для работы с категориями
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */

@RequiredArgsConstructor
@RestController
@Api(tags = "CategoryController")
@RequestMapping("/api/category")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @ApiOperation(value = "Запрос для получения всех категорий")
    @GetMapping()
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }


    @ApiOperation(value = "Запрос для создания категории")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Категория успешно создана"),
            @ApiResponse(code = 400, message = "Переданы неверные данные")
    })
    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@ApiParam(value = "DTO Категории")
                                                      @RequestBody @Valid CategoryDTO category) {
        return new ResponseEntity<>(
                categoryMapper.toDto(
                        categoryService.createOrUpdate(
                                categoryMapper.toEntity(category))), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Удаление категории")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Категория успешно удалена"),
            @ApiResponse(code = 400, message = "Переданы неверные данные"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @ApiParam(value = "Id категории", example = "1") @PathVariable long id) {
        Optional<Category> category = categoryService.getOne(id);

        if (category.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            categoryService.removeCategory(id);
            return ResponseEntity.noContent().build();
        }
    }


    @ApiOperation(value = "Обновление категории")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Категория успешно обновлена"),
            @ApiResponse(code = 400, message = "Переданы неверные данные"),
    })
    @PutMapping()
    public ResponseEntity<CategoryDTO> updateCategory(@ApiParam(value = "DTO Категории")
                                                      @RequestBody @Valid CategoryDTO category) {
        return new ResponseEntity<>(
                categoryMapper.toDto(
                        categoryService.createOrUpdate(
                                categoryMapper.toEntity(category))), HttpStatus.OK);
    }


    @ApiOperation(value = "Получение категории по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Категория успешно получена"),
            @ApiResponse(code = 400, message = "Категория не найдена"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategiryById(
            @ApiParam(value = "Id Категории", example = "1") @PathVariable long id) {
        Optional<Category> category = categoryService.getOne(id);

        if (category.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
    }


}
