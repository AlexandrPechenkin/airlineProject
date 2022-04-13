package app.services.impl;

import app.entities.Registration;
import app.entities.Ticket;
import app.repositories.RegistrationRepository;
import app.repositories.TicketRepository;
import app.services.interfaces.RegistrationService;
import app.services.interfaces.SeatService;
import app.services.interfaces.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с классом {@link Registration}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final TicketService ticketService;
    private final SeatService seatService;

    /**
     * Создание/обновление записи в БД о регистрации.
     *
     * @param holdNumber - номер брони
     * @param seatId - идентификатор места
     * @return {@link Registration}
     */
    @Override
    public Registration createRegistrationByHoldNumberAndSeatId(Long holdNumber, Long seatId) {
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

        try {
            Ticket ticket = ticketService.findTicketByHoldNumber(holdNumber);

            // вычисление разницы между датами, чтобы определить, можно ли начинать регистрацию пассажира на рейс
            LocalDateTime nowTime = LocalDateTime.now();

            //LocalDate regDate = ticket.getFlight().getDepartureDate();
            LocalDate regDate = nowTime.toLocalDate().plusDays(1L);
            LocalTime regTime = ticket.getFlight().getDepartureTime();
            LocalDateTime regDateTime = LocalDateTime.of(regDate, regTime);

            Period period = Period.between(regDate, nowTime.toLocalDate());
            period = period.minusDays(nowTime.toLocalTime().compareTo(regTime) >= 0 ? 0 : 1);
            //Duration duration = Duration.between(regTime, LocalDateTime.now());
            //duration = duration.minusDays(duration.toDaysPart());
            // если до рейса осталось <= 30 часов И билет не просрочен, то регистрация разрешена и создаётся
            // иначе, регистрация происходит либо раньше, либо позже времени вылета и не сохраняется
            if (period.getYears() == 0 && period.getMonths() == 0 && (period.getDays() <= 1 && period.getDays() >= 0)) {
                ticket.setSeat(seatService.getSeatById(seatId).orElse(null));
                if (ticket.getSeat() == null) {
                    log.error("Ошибка при задании места в билете в процессе регистрации." +
                            "Объект Seat с заданным id отсутствует в БД");
                    return null;
                } else {
                    ticketService.createOrUpdateTicket(ticket);
                    return registrationRepository.save(
                            Registration.builder()
                                    .ticket(ticket)
                                    .status("OK")
                                    .registrationDateTime(LocalDateTime.now())
                                    .build()
                    );
                }
            } else {
                return null;
            }


        } catch (NullPointerException npe) {
            npe.printStackTrace();
            log.error("Ошибка при поиске билета в процессе регистрации." +
                    "Объект Ticket с заданным номером брони отсутствует в БД");
            return null;
        }
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
