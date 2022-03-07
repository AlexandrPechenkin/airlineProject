package app.services.passenger;

import app.entities.passenger.Passenger;
import app.repositories.passenger.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с {@link Passenger}
 * @author Александр Данилов
 * @version 0.1
 */

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;

    /**
     * Создает и обновляет пассажира
     * @param passenger - Пассажир
     * @return {@link Passenger}
     */
    @Override
    public Passenger createOrUpdatePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    /**
     * Получение пассажира по id
     * @param id - Уникальный идентификатор пассажира
     * @return {@link Passenger}
     */
    @Override
    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

}
