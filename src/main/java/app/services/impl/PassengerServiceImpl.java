package app.services.impl;

import app.entities.Passenger;
import app.repositories.PassengerRepository;
import app.services.interfaces.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PasswordEncoder passwordEncoder;

    /**
     * Создает и обновляет пассажира
     * @param passenger - Пассажир
     * @return {@link Passenger}
     */
    @Override
    @Transactional
    public Passenger createOrUpdatePassenger(Passenger passenger) {
        passenger.setPassword(passwordEncoder.encode(passenger.getPassword()));
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
