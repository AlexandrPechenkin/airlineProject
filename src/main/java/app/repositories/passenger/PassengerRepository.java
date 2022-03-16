package app.repositories.passenger;

import app.entities.clients.passenger.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий Passenger
 * @author Александр Данилов
 * @version 0.1
 */
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    /**
     * Нахождение пассажира по ID
     * @param id - Уникальный идентификатор пассажира
     */
    Optional<Passenger> findById(Long id);
}
