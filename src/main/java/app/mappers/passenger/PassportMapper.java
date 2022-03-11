package app.mappers.passenger;

import app.entities.passenger.Passport;
import app.entities.passenger.dto.PassportDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Маппер MapStruct для Паспорта
 * @author Александр Данилов
 * @version 0.1
 */

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PassportMapper {
    /**
     * Метод сопоставляет Passport c PassportDTO
     * @param passport - Пассажир
     */
    PassportDTO toDto(Passport passport);

    /**
     * Метод сопоставляет PassportDTO c Passport
     * @param passportDTO - Поля паспорта, которые отдаются наружу
     */
    Passport toEntity(PassportDTO passportDTO);
}
