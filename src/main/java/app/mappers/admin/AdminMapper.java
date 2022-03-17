package app.mappers.admin;

import app.entities.users.admin.Admin;
import app.entities.users.admin.dto.AdminDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования классов Admin и AdminDTO друг в друга.
 */
@Component
@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {

    /**
     * Метод приводит Admin к AdminDTO.
     *
     * @param admin
     * @return
     */
    AdminDTO toDto(Admin admin);

    /**
     * Метод приводит AdminDTO к Admin.
     *
     * @param adminDTO
     * @return
     */
    Admin toEntity(AdminDTO adminDTO);

}
