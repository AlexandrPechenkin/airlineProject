package app.services;

import app.entities.Category;

import java.util.List;

/**
 * Интерфейс Category
 * @Autor Alexandr Pechenkin
 * @version 1.0
 */

public interface CategoryService {
    /** Метод получения всех категорий */
    List<Category> getAllCategory();

    /** Метод добавления категории */
    void addCategory(Category category);

    /** Метод удаления категории */
    void removeCategory(Category category);
}
