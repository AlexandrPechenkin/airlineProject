package app.entities.registration;

import app.entities.booking.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс, описывающий процесс регистрирования пользователем,
 * а также передаёт данные о времени сущности Booking для расчёта доступного времени
 * бронирования билета в зависимости от выбранного пользователем способа оплаты.
 */
@Entity
@Table(name = "Registration")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class Registration {

    /**
     * Уникальный идентификатор процесса регистрирования рейса пользователем.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Регистрация на рейс происходит, если у пользователя есть бронирование этого рейса.
     */
    @OneToOne
    private Booking booking;

    /**
     * Время регистрации пассажира на рейс.
     * Регистрироваться можно не ранее чем за 30 часов до вылета.
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime registrationDateTime;
}
