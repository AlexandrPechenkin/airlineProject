package app.services.interfaces;

import app.entities.Registration;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для класса {@link Registration}.
 */
public interface RegistrationService {
    /**
     * Создание/обновление/удаление записи в БД о регистрации.
     *
     * @param registration - регистрация
     * @return {@link Registration}
     */
    Registration createOrUpdateOrDeleteRegistration(Registration registration);

    /**
     * Возвращает запись о регистрации по id.
     *
     * @param id - уникальный идентификатор для {@link Registration}
     * @return {@link Optional<Registration>}
     */
    Optional<Registration> findById(long id);

    /**
     * Получение всех записей в БД о регистрациях.
     *
     * @return {@link List<Registration>}
     */
    List<Registration> findAll();

    /**
     * Удаление записи в БД о регистрации по id.
     *
     * @param id - - уникальный идентификатор для {@link Registration}
     */
    void deleteRegistrationById(long id);
}
