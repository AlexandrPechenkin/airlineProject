package app.repositories.destination;

import app.entities.destination.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий аэропорта
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    /**
     * Возвращает аэропорт по ID
     * @param id - ID аэропорта
     * @return - аэропорт
     */
    @Override
    Optional<Destination> findById(Long id);

    /**
     * Возвращает список аэропортов по названию города
     * @param city - город, в котором находится аэропорт
     * @return - список аэропортов
     */
    List<Destination> getDestinationListByCity(String city);

    /**
     * Возвращает список аэропортов по названию страны
     * @param countryName - страна, в которой находится аэропорт
     * @return - список аэропортов
     */
    List<Destination> getDestinationListByCountryName(String countryName);
}
