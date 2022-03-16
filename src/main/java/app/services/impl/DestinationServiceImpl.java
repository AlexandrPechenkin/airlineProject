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
     * Возвращает аэропорт по ID
     * @param id - ID аэропорта
     * @return - аэропорт
     */
    @Override
    public Optional<Destination> getDestinationById(Long id) {
        return destinationRepository.findById(id);
    }

    /**
     * Возвращает список аэропортов по названию города
     * @param city - город, в котором находится аэропорт
     * @return - список аэропортов
     */
    @Override
    public List<Destination> getDestinationListByCity(String city) {
        return destinationRepository.getDestinationListByCity(city);
    }

    /**
     * Возвращает список аэропортов по названию страны
     * @param countryName - страна, в которой находится аэропорт
     * @return - список аэропортов
     */
    @Override
    public List<Destination> getDestinationListByCountryName(String countryName) {
        return destinationRepository.getDestinationListByCountryName(countryName);
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
