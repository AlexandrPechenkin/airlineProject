package app.entities.registration;

import app.entities.passenger.Passenger;
import app.entities.ticket.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Класс, описывающий процесс регистрирования на рейс (выбор конкретного билета из
 * забронированного пассажиром рейса) и условия, при которых возможна регистрация
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
     * Выбранные пассажиром билеты на забронированные рейсы.
     */
    @OneToMany(targetEntity = Ticket.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Map<Passenger, List<Ticket>> passengerTickets;

    /**
     * Статус регистрации.
     * "OK" - регистрация на рейс успешно выполнена.
     */
    @Column(name = "status")
    @Value("IN_PROGRESS")
    private String status;

    /**
     * Время регистрации пассажира на рейс.
     * Регистрироваться можно не ранее чем за 30 часов до вылета.
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime registrationDateTime;
}
