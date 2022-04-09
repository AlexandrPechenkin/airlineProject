package app.services.interfaces;

import app.entities.Seat;

import java.util.List;
import java.util.Optional;

public interface SeatService {
//
//    /**
//     * Метод получения мест по рейсу
//     *
//     * @param flightId - уникальный идентификатор рейса
//     */
//    List<Seat> getAllSeatByFlightId(long flightId);

    /**
     * Метод создания и обновления места
     *
     * @param seat - место
     */
    Seat createOrUpdate(Seat seat);

    /**
     * получить место по id
     *
     * @param id - идентификатор места
     * @return {@link Seat}
     */
    Optional<Seat> getSeatById(Long id);

//    /**
//     * Метод получения количества непроданных мест
//     *
//     * @param fligthId - уникальный идентификатор рейса
//     */
//
//    Long getCountNoSoldSeat(long fligthId);

//    /**
//     * Метод получения проданных мест на рейсе
//     *
//     * @param fligthId - уникальный идентификатор рейса
//     */
//    Long getCountSoldSeat(long fligthId);


//    /**
//     * Метод получения количества зарегистрированных пассажиров
//     *
//     * @param fligthId - уникальный идентификатор рейса
//     */
//    Long getCountRegisteredPassenger(long fligthId);

//    /**
//     * Получение мест по рейсу и категории
//     *
//     * @param flightId   - уникальный идентификатор рейса
//     * @param categoryId - уникальный идентификатор категории
//     */
//
//    List<Seat> getSeatByFlightAndCategory(long flightId, long categoryId);
}
