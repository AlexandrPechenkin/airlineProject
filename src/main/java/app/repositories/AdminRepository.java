package app.repositories;

import app.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для класса Admin.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
