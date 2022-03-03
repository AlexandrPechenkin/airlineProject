package app.config;

import app.entities.Category;
import app.repositories.CategoryRepository;
import app.services.CategoryService;
import app.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@Component
public class DataInitializer {

    final
    CategoryService categoryService;

    public DataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    Category categoryEconomy = new Category("Economy");
    Category categoryComfort = new Category("Comfort");
    Category categoryBusiness = new Category("Business");
    Category categoryFirstClass = new Category("First class");



    @PostConstruct
    public void init() {
        categoryService.addCategory(categoryEconomy);
        categoryService.addCategory(categoryComfort);
        categoryService.addCategory(categoryBusiness);
        categoryService.addCategory(categoryFirstClass);

        System.out.println("Категории добавлены");



        System.out.println("DataInitializer сработал!");
    }
}
