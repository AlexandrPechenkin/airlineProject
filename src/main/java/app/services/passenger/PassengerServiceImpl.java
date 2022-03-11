package app.services.passenger;

import app.entities.passenger.Passenger;
import app.repositories.passenger.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервис для работы с {@link Passenger}
 * @author Александр Данилов
 * @version 0.1
 */

//TODO: Пересмотреть структуру пакета services. Вместо отдельных пакетов для сущностей создать пакеты interfaces и impl. Раскладывать сервисы туда по типу.

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
    @Transactional
    public Passenger createOrUpdatePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    /**
     * Получение пассажира по id
     * @param id - Уникальный идентификатор пассажира
     * @return {@link Passenger}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Passenger> findById(Long id) {
        return passengerRepository.findById(id);
    }

}
