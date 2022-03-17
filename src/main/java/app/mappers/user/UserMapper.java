package app.mappers.user;

import app.entities.users.passenger.Passenger;
import app.entities.users.user.User;
import app.entities.users.user.dto.UserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов User и UserDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    /**
     * Преобразование User к UserDTO.
     *
     * @param user
     * @return
     */
    UserDTO toDto(User user);

    /**
     * Преобразование UserDTO к User.
     *
     * @param userDTO
     * @return
     */
    User toEntity(UserDTO userDTO);


    default User createUser() {
        return new Passenger();
    }
}
