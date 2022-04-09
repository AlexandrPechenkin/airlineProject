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
     * @param holdNumber - номер брони
     * @param seatId - идентификатор места
     * @return {@link Registration}
     */
    Registration createRegistrationByHoldNumberAndSeatId(Long holdNumber, Long seatId);

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
