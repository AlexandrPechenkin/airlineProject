package app.entities.mappers.registration;

import app.entities.Registration;
import app.entities.dtos.RegistrationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов Registration и RegistrationDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RegistrationMapper {
    /**
     * Преобразование Registration к RegistrationDTO.
     *
     * @param registration - данные, которые передаются с бэкенда.
     * @return {@link Registration}
     */
    RegistrationDTO toDto(Registration registration);

    /**
     * Преобразование RegistrationDTO к Registration.
     *
     * @param registrationDTO - данные, которые передаются с фронтенда.
     * @return {@link RegistrationDTO}
     */
    Registration toEntity(RegistrationDTO registrationDTO);
}
