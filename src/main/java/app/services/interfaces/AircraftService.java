package app.services.interfaces;

import app.entities.Aircraft;

import java.util.Optional;

/**
 * Интерфейс сервиса для работы с Воздушным судном
 * @author Александр Данилов
 * @version 0.1
 */
public interface AircraftService {
    /**
     * Метод создания и обновления воздушного судна
     * @param aircraft - Воздушное судно
     * @return {@link Aircraft}
     */
    Aircraft createOrUpdateAircraft(Aircraft aircraft);

    /**
     * Метод создания и обновления воздушного судна
     * @param id - Уникальный ID воздушного судна
     * @return {@link Aircraft}
     */
    Optional<Aircraft> getAircraftById(Long id);

    /**
     * Метод удаления воздушного судна по ID
     * @param id - Уникальный ID воздушного судна
     */
    void deleteAircraftById(Long id);
}
