package app.services.category;

import app.entities.category.Category;

import java.util.List;

/**
 * Интерфейс Category
 * @author Alexandr Pechenkin
 * @version 1.1
 */

public interface CategoryService {
    /** Метод получения всех категорий */
    List<Category> getAllCategory();

    /** Метод добавления категории */
    void addCategory(Category category);

    /** Метод удаления категории */
    void removeCategory(Category category);

}
