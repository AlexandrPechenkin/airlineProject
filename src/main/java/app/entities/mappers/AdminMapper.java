package app.entities.mappers;

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
     */
    AdminDTO toDto(Admin admin);

    /**
     * Приведение AdminDTO к Admin.
     *
     * @param adminDTO - данные, которые передаются с фронтенда.
     */
    Admin toEntity(AdminDTO adminDTO);

}
