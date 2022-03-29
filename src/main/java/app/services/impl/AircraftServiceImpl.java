package app.services.impl;

import app.entities.Aircraft;
import app.repositories.AircraftRepository;
import app.services.interfaces.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с {@link Aircraft}
 * @author Александр Данилов
 * @version 0.1
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AircraftServiceImpl implements AircraftService {
    private final AircraftRepository aircraftRepository;

    /**
     * Реализация создания и обновления воздушного судна
     * @param aircraft - Воздушное судно
     */
    @Override
    public Aircraft createOrUpdateAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    /**
     * Реализация получения воздушного судна по уникальному ID
     * @param id - Уникальный ID воздушного судна
     */
    @Override
    public Optional<Aircraft> getAircraftById(Long id) {
        return aircraftRepository.findAircraftById(id);
    }

    /**
     * Удаление воздушного судна по его ID
     * @param id - Уникальный ID воздушного судна
     */
    @Override
    public void deleteAircraftById(Long id) {
        aircraftRepository.deleteById(id);
    }

    /**
     * Возвращает список всех воздушных судов
     * @return - список всех воздушных судов
     */
    @Override
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }
}
