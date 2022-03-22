package app.repositories;

import app.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий Seat
 * @author Alexandr Pechenkin
 * @version  1.0
 * */
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}