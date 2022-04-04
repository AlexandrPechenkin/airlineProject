package app.services.impl;

import app.entities.Seat;
import app.repositories.FlightRepository;
import app.repositories.SeatRepository;
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
     * Метод создания или обновления места
     *
     * @param seat - место
     */
    @Transactional
    @Override
    public Seat createOrUpdate(Seat seat) {

        return seatRepository.save(seat);
    }

}
