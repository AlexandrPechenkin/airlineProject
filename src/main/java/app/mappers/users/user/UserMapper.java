package app.mappers.users.user;

import app.entities.users.admin.Admin;
import app.entities.users.admin.dto.AdminDTO;
import app.entities.users.airlineManager.AirlineManager;
import app.entities.users.airlineManager.dto.AirlineManagerDTO;
import app.entities.users.passenger.Passenger;
import app.entities.users.passenger.dto.PassengerDTO;
import app.entities.users.user.User;
import app.entities.users.user.dto.UserDTO;
import app.mappers.users.admin.AdminMapper;
import app.mappers.users.airlineManager.AirlineManagerMapper;
import app.mappers.users.passenger.PassengerMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов User и UserDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.FIELD, implementationName = "UserMapperImpl",
        uses = {PassengerMapper.class, AdminMapper.class, AirlineManagerMapper.class})
//@Primary
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Преобразование подклассов User к подклассу UserDTO.
     *
     * @param user
     * @return
     */
    default UserDTO toDto(User user) {
        if (user instanceof Passenger) {
            return ((PassengerMapper)INSTANCE).passengerEntityToPassengerDto((Passenger) user);
        } else if (user instanceof Admin) {
            return ((AdminMapper)INSTANCE).adminEntityToAdminDto((Admin) user);
        } else if (user instanceof AirlineManager) {
            return ((AirlineManagerMapper)INSTANCE).airlineManagerEntityToAirlineManagerDto((AirlineManager) user);
        }
        return null;
    }

    /**
     * Преобразование подкласса UserDTO к подклассу User.
     *
     * @param userDTO
     * @return
     */
    default User toEntity(UserDTO userDTO) {
        if (userDTO instanceof PassengerDTO) {
            return ((PassengerMapper)INSTANCE).passengerDtoToPassengerEntity((PassengerDTO) userDTO);
        } else if(userDTO instanceof AdminDTO) {
            return ((AdminMapper)INSTANCE).adminDtoToAdminEntity((AdminDTO) userDTO);
        } else if (userDTO instanceof AirlineManagerDTO) {
            return ((AirlineManagerMapper)INSTANCE).airlineManagerDtoToAirlineManagerEntity((AirlineManagerDTO) userDTO);
        }
        return null;
    }
}
