package app.services.interfaces;

import app.entities.users.passenger.Passenger;

import java.util.Optional;

/**
 * Интерфейс сервиса для работы с пассажиром
 * @author Александр Данилов
 * @version 0.1
 */
public interface PassengerService {
    /**
     * Создание пассажира
     * @param passenger Пассажир
     * @return {@link Passenger}
     */
    Passenger createOrUpdatePassenger(Passenger passenger);

    /**
     * Получение пассажира по id
     * @param id - Уникальный идентификатор пассажира
     * @return {@link Passenger}
     */
    Optional<Passenger> findById(Long id);
}
