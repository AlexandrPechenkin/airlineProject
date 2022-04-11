package app.services.impl;


import app.entities.Role;
import app.repositories.RoleRepository;
import app.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с классом {@link Role}
 */
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Создать или обновить роль
     * @param role роль
     * @return {@link Role}
     */
    @Override
    public Role createOrUpdateRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Получить роль по id
     * @param id идентификатор роли
     * @return Optional of {@link Role}
     */
    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    /**
     * Получить все роли
     * @return List of {@link Role}
     */
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Удалить роль
     * @param role роль
     */
    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }
}
