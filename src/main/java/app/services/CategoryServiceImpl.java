package app.services;

import app.entities.Category;
import app.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный слой Category
 * Для тестового заполнения таблицы и, возможно, предоставления администратору редактирования категорий
 * @Autor Alexandr Pechenkin
 * @version 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService{

    /** Создание объекта CategoryRepository */
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /** Метод получения всех категорий */
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    /** Метод добавления категории */
    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    /** Метод удаления категории */
    @Override
    public void removeCategory(Category category) {
        categoryRepository.delete(category);
    }
}
