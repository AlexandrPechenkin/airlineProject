package app.repositories;

import app.entities.Category;
import app.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий Category
 * @author Alexandr Pechenkin
 * @version  1.0
 * */

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    @Query(value = "select c from Category c where (c.category like concat('%', :category, '%'))")
    Category getCategoryByString (String category);
}
