package app.entities.mappers;

import app.entities.Admin;
import app.entities.dtos.AdminDTO;
import app.entities.AirlineManager;
import app.entities.dtos.AirlineManagerDTO;
import app.entities.Passenger;
import app.entities.dtos.PassengerDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования подклассов User и DTO подклассов User друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR, implementationName = "UserMapperImpl",
        uses = {PassengerMapper.class, AdminMapper.class, AirlineManagerMapper.class})
public interface UserMapper {
    PassengerDTO toPassengerDto(Passenger passenger);
    Passenger toPassengerEntity(PassengerDTO passengerDTO);
    AdminDTO toAdminDto(Admin admin);
    Admin toAdminEntity(AdminDTO adminDTO);
    AirlineManagerDTO toAirlineManagerDto(AirlineManager airlineManager);
    AirlineManager toAirlineManagerEntity(AirlineManagerDTO airlineManagerDTO);
}
