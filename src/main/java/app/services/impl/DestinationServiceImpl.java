package app.services.impl;

import app.entities.destination.Destination;
import app.repositories.destination.DestinationRepository;
import app.services.interfaces.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с аэропортом
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    /**
     * Репозиторий аэропорта
     */
    private final DestinationRepository destinationRepository;

    /**
     * Возвращает объект Optional с аэропортом или null по ID
     * @param id - ID аэропорта
     * @return - объект Optional с аэропортом или null
     */
    @Override
    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    /**
     * Возвращает коллекцию аэропортов по названию города
     * @param city - город, в котором находится аэропорт
     * @return - коллекция аэропортов
     */
    @Override
    public List<Destination> getListOfDestinationsByCity(String city) {
        return destinationRepository.getDestinationsByCity(city);
    }

    /**
     * Возвращает коллекцию аэропортов по названию страны
     * @param countryName - страна, в которой находится аэропорт
     * @return - коллекция аэропортов
     */
    @Override
    public List<Destination> getListOfDestinationsByCountryName(String countryName) {
        return destinationRepository.getDestinationsByCountryName(countryName);
    }

    /**
     * Создает или обновляет аэропорт
     * @param destination - объект аэропорта, который нужно создать или обновить
     * @return - объект аэропорта, который был создан или обновлен
     */
    @Override
    public Destination createOrUpdateDestination(Destination destination) {
        return destinationRepository.save(destination);
    }
}
