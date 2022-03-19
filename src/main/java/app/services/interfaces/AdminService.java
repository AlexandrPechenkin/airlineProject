package app.services.interfaces;


import app.entities.Admin;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса для класса {@link Admin}.
 */
public interface AdminService {
    /**
     * Создать/обновить запись об администраторе в БД.
     *
     * @param admin - администратор
     * @return {@link Admin}
     */
    Admin createOrUpdateAdmin(Admin admin);

    /**
     * Получить запись об администраторе из БД по id.
     *
     * @param id - уникальный идентификатор администратора.
     * @return {@link Optional<Admin>}
     */
    Optional<Admin> findById(long id);

    /**
     * Получить все записи об администраторах из БД.
     *
     * @return {@link List<Admin>}
     */
    List<Admin> findAll();

    /**
     * Удалить запись об администраторе из БД по id.
     *
     * @param id - - уникальный идентификатор администратора.
     */
    void deleteAdminById(long id);
}
