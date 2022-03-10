package app.repositories.category;

import app.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий Category
 * @author Alexandr Pechenkin
 * @version  1.0
 * */

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
}
