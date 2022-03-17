package app.services.impl;

import app.entities.category.Category;
import app.repositories.category.CategoryRepository;
import app.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с {@link Category}
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Метод получения всех категорий
     */
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    /**
     * Создает и обновляет категорию
     *
     * @param category - категория
     * @return {@link Category}
     */
    @Transactional
    @Override
    public Category createOrUpdate(Category category) {
        return categoryRepository.save(category);
    }


    /**
     * Метод удаляет категорию по id
     *
     * @param id - уникальный идентификатор категории
     */
    @Transactional
    @Override
    public void removeCategory(long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Получение категории по id
     *
     * @param id - Уникальный идентификатор категории
     * @return {@link Category}
     */
    @Override
    public Optional<Category> getOne(long id) {
        return categoryRepository.findById(id);
    }

}
