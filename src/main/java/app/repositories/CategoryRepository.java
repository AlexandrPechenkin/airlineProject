package app.repositories;

import app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий Category
 * @Autor Alexandr Pechenkin
 * @version  1.0
 * */

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
}
