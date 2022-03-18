package app.services.impl;

import app.entities.seat.Seat;
import app.repositories.flight.FlightRepository;
import app.repositories.seat.SeatRepository;
import app.services.interfaces.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с {@link Seat}
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */
@Transactional
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    public SeatServiceImpl(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    /**
     * Метод получения всех мест рейса
     */
    @Override
    public List<Seat> getAllSeatByFlightId(long flightId) {

        return seatRepository.findAll()
                .stream()
                .filter(seat -> seat.getFlight().getId() == flightId)
                .collect(Collectors.toList());
    }

    /**
     * Метод создания или обновления места
     *
     * @param seat - место
     */
    @Transactional
    @Override
    public Seat createOrUpdate(Seat seat) {

        return seatRepository.save(seat);
    }


    /**
     * Метод для получения количества непроданных мест на рейсе
     *
     * @param fligthId - Уникальный идентификатор Flight
     */

    @Override
    public Long getCountNoSoldSeat(long fligthId) {
        List<Seat> list = seatRepository.findAll();
        return list.stream()
                .filter(seat -> seat.getFlight().getId() == fligthId)
                .filter(seat -> !seat.getIsSold())
                .count();
    }

    /**
     * Метод для получения количества проданных билетов
     *
     * @param fligthId - уникальный идентификатор рейса
     */
    @Override
    public Long getCountSoldSeat(long fligthId) {
        List<Seat> list = seatRepository.findAll();
        return list.stream()
                .filter(seat -> seat.getFlight().getId() == fligthId)
                .filter(Seat::getIsSold)
                .count();
    }

    /**
     * Метод для получения зарегистрированных пассажиров
     *
     * @param fligthId - уникальный идентификатор рейса
     */
    @Override
    public Long getCountRegisteredPassenger(long fligthId) {
        List<Seat> list = seatRepository.findAll();
        return list.stream()
                .filter(seat -> seat.getFlight().getId() == fligthId)
                .filter(Seat::getIsRegistered)
                .count();
    }

    /**
     * Метод получения мест по рейсу и категории
     *
     * @param flightId   - уникальный идентификатор рейса
     * @param categoryId - уникальный идентификатор категории
     */
    @Override
    public List<Seat> getSeatByFlightAndCategory(long flightId, long categoryId) {

        return seatRepository.findAll()
                .stream()
                .filter(seat -> seat.getFlight().getId() == flightId)
                .filter(seat -> seat.getCategory().getId() == categoryId)
                .collect(Collectors.toList());
    }
}