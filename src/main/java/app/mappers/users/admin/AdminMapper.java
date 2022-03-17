package app.mappers.users.admin;

import app.entities.users.admin.Admin;
import app.entities.users.admin.dto.AdminDTO;
import app.mappers.users.user.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов Admin и AdminDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        implementationName = "AdminMapperImpl")
public interface AdminMapper extends UserMapper {

    /**
     * Метод приводит Admin к AdminDTO.
     *
     * @param admin
     * @return
     */
    AdminDTO adminEntityToAdminDto(Admin admin);

    /**
     * Метод приводит AdminDTO к Admin.
     *
     * @param adminDTO
     * @return
     */
    Admin adminDtoToAdminEntity(AdminDTO adminDTO);

}
