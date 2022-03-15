package app.services.interfaces;

import app.entities.destination.Destination;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для работы с аэропортом
 */
public interface DestinationService {

    /**
     * Возвращает объект Optional с аэропортом или null по ID
     * @param id - ID аэропорта
     * @return - объект Optional с аэропортом или null
     */
    Optional<Destination> getDestinationById(Long id);

    /**
     * Возвращает коллекцию аэропортов по названию города
     * @param city - город, в котором находится аэропорт
     * @return - коллекция аэропортов
     */
    List<Destination> getListOfDestinationsByCity(String city);

    /**
     * Возвращает коллекцию аэропортов по названию страны
     * @param countryName - страна, в которой находится аэропорт
     * @return - коллекция аэропортов
     */
    List<Destination> getListOfDestinationsByCountryName(String countryName);

    /**
     * Создает или обновляет аэропорт
     * @param destination - объект аэропорта, который нужно создать или обновить
     * @return - объект аэропорта, который был создан или обновлен
     */
    Destination createOrUpdateDestination(Destination destination);
}
