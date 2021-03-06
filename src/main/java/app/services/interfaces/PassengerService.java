package app.services.interfaces;

import app.entities.Admin;
import app.entities.Passenger;

import java.util.List;
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


    List<Passenger> findAll();

    void deletePassengerById(long id);


}
