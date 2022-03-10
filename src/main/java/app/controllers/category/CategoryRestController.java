package app.controllers.category;

import app.entities.category.Category;
import app.services.category.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Рест контроллер для сущности Category
 * @author Alexandr Pechenkin
 * @version 1.0
 */

@RestController
@Api(tags = "CategoryController")
@RequestMapping("/category")
public class CategoryRestController {

    /** Создание объекта CategoryService */
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /** Получение всех категорий */
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    /** Добавление новой категории */
    @PostMapping("/create")
    public ResponseEntity<Void> createCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** Удаление категории */
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteCategory(@RequestBody Category category) {
        categoryService.removeCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** Обновление категории */
    @PostMapping("/update")
    public ResponseEntity<Void> updateCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
