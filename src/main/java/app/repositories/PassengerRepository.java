package app.repositories;

import app.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий Passenger
 * @author Александр Данилов
 * @version 0.1
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    /**
     * Нахождение пассажира по ID
     * @param id - Уникальный идентификатор пассажира
     */
    Optional<Passenger> findById(Long id);
}
