package app.services.impl;

import app.entities.Registration;
import app.entities.Ticket;
import app.repositories.RegistrationRepository;
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
     * получение регистрации по номеру брони
     *
     * @param holdNumber - номер брони
     * @return {@link String}
     */
    @Override
    public String getRegistrationStatusByHoldNumber(Long holdNumber) {
        try {
            return registrationRepository.getRegistrationStatusByHoldNumber(holdNumber).getStatus();
        } catch (NullPointerException npe) {
            return null;
        }
    }

    /**
     * Создание/обновление записи в БД о регистрации.
     *
     * @param holdNumber - номер брони
     * @param seatId - идентификатор места
     * @return {@link Registration}
     */
    @Override
    public Registration createRegistrationByHoldNumberAndSeatId(Long holdNumber, Long seatId) {
        try {
            Ticket ticket = ticketService.findTicketByHoldNumber(holdNumber);

            //получение даты отправления для сравнения с временем запроса регистрации
            LocalDateTime nowTime = LocalDateTime.now();

            LocalTime regTime = nowTime.toLocalTime().plusHours(2L); //для проверки
            //LocalTime regTime = ticket.getFlight().getDepartureTime();
            LocalDate regDate = nowTime.plusHours(1L).toLocalDate(); //для проверки
            //LocalDate regDate = ticket.getFlight().getDepartureDate();
            LocalDateTime regDateTime = LocalDateTime.of(regDate, regTime); //дата отправления

            // вычисление разницы между датами, чтобы определить, можно ли начинать регистрацию пассажира на рейс
            Period period = Period.between(nowTime.toLocalDate(), regDate);
            Duration duration = Duration.between(nowTime, regDateTime);

            // если до рейса осталось <= 30 часов и => 50 минут, то регистрация разрешена и создаётся
            if (period.getYears() == 0 && period.getMonths() == 0 &&
                    duration.toMinutes() >= 50 && duration.toMinutes() <= 1800) {
                ticket.setSeat(seatService.getSeatById(seatId).orElse(null));
                if (ticket.getSeat() == null) {
                    log.error("Ошибка при задании места в билете в процессе регистрации." +
                            "Объект Seat с заданным id отсутствует в БД");
                    return null;
                } else {
                    ticket.getSeat().setIsRegistered(true);
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
