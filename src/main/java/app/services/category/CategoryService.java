package app.services.category;

import app.entities.category.Category;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс Category
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */

public interface CategoryService {
    /**
     * Метод получения всех категорий
     */
    List<Category> getAllCategory();

    /**
     * Метод добавления категории
     */
    Category createOrUpdate(Category category);

    /**
     * Метод удаления категории
     */
    void removeCategory(long id);

    /**
     * Получение категории по id
     */
    Optional<Category> getOne(long id);

}
