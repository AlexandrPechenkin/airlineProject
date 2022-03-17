package app.mappers.users.airlineManager;

import app.entities.users.airlineManager.AirlineManager;
import app.entities.users.airlineManager.dto.AirlineManagerDTO;
import app.mappers.users.user.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        implementationName = "AirlineManagerImpl")
public interface AirlineManagerMapper extends UserMapper {

    AirlineManagerDTO airlineManagerEntityToAirlineManagerDto(AirlineManager airlineManager);
    AirlineManager airlineManagerDtoToAirlineManagerEntity(AirlineManagerDTO airlineManagerDTO);
}
