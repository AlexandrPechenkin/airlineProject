package app.mappers.registration;

import app.entities.registration.Registration;
import app.entities.registration.dto.RegistrationDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RegistrationMapper {
    RegistrationDTO toDto(Registration registration);
    Registration toEntity(RegistrationDTO registrationDTO);
}
