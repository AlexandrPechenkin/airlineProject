package app.services.impl;

import app.entities.AirlineManager;
import app.repositories.AirlineManagerRepository;
import app.services.interfaces.AirlineManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с классом {@link AirlineManager}
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AirlineManagerServiceImpl implements AirlineManagerService {
    private final AirlineManagerRepository airlineManagerRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Создание/обновление записи в БД о менеджере.
     *
     * @param airlineManager - менеджер
     * @return {@link AirlineManager}
     */
    @Override
    public AirlineManager createOrUpdateAirlineManager(AirlineManager airlineManager) {
        airlineManager.setPassword(passwordEncoder.encode(airlineManager.getPassword()));
        return airlineManagerRepository.save(airlineManager);
    }

    /**
     * Возвращает запись о менеджере по id.
     *
     * @param id - уникальный идентификатор для {@link AirlineManager}
     * @return {@link Optional<AirlineManager>}
     */
    @Override
    public Optional<AirlineManager> findById(long id) {
        return airlineManagerRepository.findById(id);
    }

    /**
     * Получение всех записей в БД о менеджерах.
     *
     * @return {@link List<AirlineManager>}
     */
    @Override
    public List<AirlineManager> findAll() {
        return airlineManagerRepository.findAll();
    }

    /**
     * Удаление записи в БД о менеджере по id.
     *
     * @param id - - уникальный идентификатор для {@link AirlineManager}
     */
    @Override
    public void deleteAirlineManagerById(long id) {
        airlineManagerRepository.deleteById(id);
    }
}
