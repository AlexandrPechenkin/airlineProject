package app.services.interfaces;

import app.entities.Destination;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для работы с аэропортом
 */
public interface DestinationService {

    /**
     * Возвращает аэропорт по ID
     * @param id - ID аэропорта
     * @return - аэропорт
     */
    Optional<Destination> getDestinationById(Long id);

    /**
     * Возвращает список аэропортов по названию города
     * @param city - город, в котором находится аэропорт
     * @return - список аэропортов
     */
    List<Destination> getDestinationListByCity(String city);

    /**
     * Возвращает список аэропортов по названию страны
     * @param countryName - страна, в которой находится аэропорт
     * @return - список аэропортов
     */
    List<Destination> getDestinationListByCountryName(String countryName);

    /**
     * Создает или обновляет аэропорт
     * @param destination - объект аэропорта, который нужно создать или обновить
     * @return - объект аэропорта, который был создан или обновлен
     */
    Destination createOrUpdateDestination(Destination destination);

    List<Destination> findAll();
}
