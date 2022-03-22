package app.entities.mappers.admin;

import app.entities.Admin;
import app.entities.dtos.AdminDTO;
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
     * Приведение Admin к AdminDTO.
     *
     * @param admin - данные, которые передаются с бэкенда.
     * return {@link AdminDTO}
     */
    AdminDTO toDto(Admin admin);

    /**
     * Приведение AdminDTO к Admin.
     *
     * @param adminDTO - данные, которые передаются с фронтенда.
     * return {@link Admin}
     */
    Admin toEntity(AdminDTO adminDTO);

}
