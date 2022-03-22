package app.services.impl;

import app.entities.Admin;
import app.repositories.AdminRepository;
import app.services.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с классом {@link Admin}
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создать/обновить запись об администраторе в БД.
     *
     * @param admin - администратор
     * @return {@link Admin}
     */
    @Override
    public Admin createOrUpdateAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    /**
     * Получить запись об администраторе из БД по id.
     *
     * @param id - уникальный идентификатор администратора.
     * @return {@link Optional<Admin>}
     */
    @Override
    public Optional<Admin> findById(long id) {
        return adminRepository.findById(id);
    }

    /**
     * Получить все записи об администраторах из БД.
     *
     * @return {@link List<Admin>}
     */
    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    /**
     * Удалить запись об администраторе из БД по id.
     *
     * @param id - - уникальный идентификатор администратора.
     */
    @Override
    public void deleteAdminById(long id) {
        adminRepository.deleteById(id);
    }
}
