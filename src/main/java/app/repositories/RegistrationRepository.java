package app.repositories;

import app.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для класса {@link Registration}.
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    /**
     * получение регистрации по номеру брони
     * @param holdNumber - номер брони
     * @return {@link Registration}
     */
    @Query(value = "select r from Registration r where r.ticket.holdNumber = :holdNumber")
    Registration getRegistrationStatusByHoldNumber(@Param("holdNumber") Long holdNumber);
}
