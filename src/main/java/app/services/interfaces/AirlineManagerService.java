package app.services.interfaces;

import app.entities.AirlineManager;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для класса {@link AirlineManager}.
 */
public interface AirlineManagerService {
    /**
     * Создание/обновление записи в БД о менеджере.
     *
     * @param airlineManager - менеджер
     * @return {@link AirlineManager}
     */
    AirlineManager createOrUpdateAirlineManager(AirlineManager airlineManager);

    /**
     * Возвращает запись о менеджере по id.
     *
     * @param id - уникальный идентификатор для {@link AirlineManager}
     * @return {@link Optional<AirlineManager>}
     */
    Optional<AirlineManager> findById(long id);

    /**
     * Получение всех записей в БД о менеджерах.
     *
     * @return {@link List<AirlineManager>}
     */
    List<AirlineManager> findAll();

    /**
     * Удаление записи в БД о менеджере по id.
     *
     * @param id - - уникальный идентификатор для {@link AirlineManager}
     */
    void deleteAirlineManagerById(long id);
}
