package app.services.impl;

import app.entities.Registration;
import app.repositories.RegistrationRepository;
import app.services.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с классом {@link Registration}
 */
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;

    /**
     * Создание/обновление записи в БД о регистрации.
     *
     * @param registration - регистрация
     * @return {@link Registration}
     */
    @Override
    public Registration createOrUpdateRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    /**
     * Возвращает запись о регистрации по id.
     *
     * @param id - уникальный идентификатор для {@link Registration}
     * @return {@link Optional<Registration>}
     */
    @Override
    public Optional<Registration> findById(long id) {
        return registrationRepository.findById(id);
    }

    /**
     * Получение всех записей в БД о регистрациях.
     *
     * @return {@link List<Registration>}
     */
    @Override
    public List<Registration> findAll() {
        return registrationRepository.findAll();
    }

    /**
     * Удаление записи в БД о регистрации по id.
     *
     * @param id - - уникальный идентификатор для {@link Registration}
     */
    @Override
    public void deleteRegistrationById(long id) {
        registrationRepository.deleteById(id);
    }
}
