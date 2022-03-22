package app.repositories;

import app.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для класса {@link Registration}.
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
