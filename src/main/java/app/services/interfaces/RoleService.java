package app.services.interfaces;

import app.entities.Role;
import app.entities.User;
import app.entities.dtos.AdminDTO;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для класса {@link Role}.
 */
public interface RoleService {
    /**
     * Создать или обновить роль
     * @param role роль
     * @return {@link Role}
     */
    Role createOrUpdateRole(Role role);

    /**
     * Получить роль по id
     * @param id идентификатор роли
     * @return Optional of {@link Role}
     */
    Optional<Role> getRoleById(Long id);

    /**
     * Получить все роли
     * @return List of {@link Role}
     */
    List<Role> getAllRoles();

    /**
     * Удалить роль
     * @param id идентификатор роли
     */
    void deleteRoleById(long id);
}
