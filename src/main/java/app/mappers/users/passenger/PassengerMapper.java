package app.mappers.users.passenger;

import app.entities.users.passenger.Passenger;
import app.entities.users.passenger.dto.PassengerDTO;
import app.mappers.users.user.UserMapper;
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
        uses = { PassportMapper.class },
        implementationName = "PassengerMapperImpl")
public interface PassengerMapper extends UserMapper {
    /**
     * Метод сопоставляет Passenger c PassengerDTO
     * @param passenger - Пассажир
     */
    PassengerDTO passengerEntityToPassengerDto(Passenger passenger);

    /**
     * Метод сопоставляет PassengerDTO c Passenger
     * @param passengerDTO - Поля пассажира которые отдаются наружу
     */
    Passenger passengerDtoToPassengerEntity(PassengerDTO passengerDTO);
}
