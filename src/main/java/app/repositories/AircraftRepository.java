package app.repositories;

import app.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий Aircraft
 * @author Александр Данилов
 * @version 0.1
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    /**
     * Нахождение воздушное судно по ID
     * @param id - Уникальный ID воздушного судна
     */
    Optional<Aircraft> findAircraftById(Long id);
}
