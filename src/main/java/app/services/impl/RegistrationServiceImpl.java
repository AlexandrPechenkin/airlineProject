package app.services.impl;

import app.entities.Registration;
import app.repositories.RegistrationRepository;
import app.repositories.TicketRepository;
import app.services.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с классом {@link Registration}
 */
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TicketRepository ticketRepository;

    /**
     * Создание/обновление записи в БД о регистрации.
     *
     * @param reg - регистрация
     * @return {@link Registration}
     */
    @Override
    public Registration createOrUpdateOrDeleteRegistration(Registration reg) {
        /*// если регистрация для пассажира на рейс только началась
        if (reg.getStatus().equals("IN_PROGRESS")) {
            // вычисление разницы между датами, чтобы определить, можно ли начинать регистрацию пассажира на рейс
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime regTime = reg.getRegistrationDateTime();
            Period period = Period.between(regTime.toLocalDate(), now.toLocalDate());
            period = period.minusDays(now.toLocalTime().compareTo(regTime.toLocalTime()) >= 0 ? 0 : 1);
            Duration duration = Duration.between(regTime, LocalDateTime.now());
            duration = duration.minusDays(duration.toDaysPart());
            // если до рейса осталось <= 30 часов И билет не просрочен, то регистрация разрешена и создаётся
            // иначе, регистрация происходит либо раньше, либо позже времени вылета и не сохраняется
            if (period.getYears() == 0 && period.getMonths() == 0 && (period.getDays() <= 1 && period.getDays() >= 0)) {
                reg.setStatus("OK");
                return registrationRepository.save(reg);
            } else {
                return null;
            }
        }
        // если регистрация отменена
        if (reg.getStatus().equals("CANCELLED")) {
            deleteRegistrationById(reg.getId());
           // логика для занесения в историю отменённых рейсов/регистраций пассажира, если нужна
           return null;
        }
        return null;*/
        reg.setRegistrationDateTime(LocalDateTime.now());
        ticketRepository.save(reg.getTicket());
        return registrationRepository.save(reg);
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
     * @param id - уникальный идентификатор для {@link Registration}
     */
    @Override
    public void deleteRegistrationById(long id) {
        registrationRepository.deleteById(id);
    }
}
