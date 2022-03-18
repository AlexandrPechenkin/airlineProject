package app.entities.mappers;

import app.entities.Passenger;
import app.entities.dtos.PassengerDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Маппер MapStruct для Пассажира
 * @author Александр Данилов
 * @version 0.1
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = { PassportMapper.class })
public interface PassengerMapper {
    /**
     * Метод сопоставляет Passenger c PassengerDTO
     * @param passenger - Пассажир
     */
    PassengerDTO toDto(Passenger passenger);

    /**
     * Метод сопоставляет PassengerDTO c Passenger
     * @param passengerDTO - Поля пассажира которые отдаются наружу
     */
    Passenger toEntity(PassengerDTO passengerDTO);
}
