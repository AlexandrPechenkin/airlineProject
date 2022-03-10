package app.services.category;

import app.entities.category.Category;
import app.repositories.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервисный слой Category
 * Для тестового заполнения таблицы и, возможно, предоставления администратору редактирования категорий
 * @author Alexandr Pechenkin
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
    @Transactional
    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    /** Метод удаления категории */
    @Transactional
    @Override
    public void removeCategory(Category category) {
        categoryRepository.delete(category);
    }

}
