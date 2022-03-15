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
     * Возвращает объект Optional с аэропортом или null по ID
     * @param id - ID аэропорта
     * @return - объект Optional с аэропортом или null
     */
    @Override
    Optional<Destination> findById(Long id);

    /**
     * Возвращает коллекцию аэропортов по названию города
     * @param city - город, в котором находится аэропорт
     * @return - коллекция аэропортов
     */
    List<Destination> getDestinationsByCity(String city);

    /**
     * Возвращает коллекцию аэропортов по названию страны
     * @param countryName - страна, в которой находится аэропорт
     * @return - коллекция аэропортов
     */
    List<Destination> getDestinationsByCountryName(String countryName);
}
