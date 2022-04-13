package app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс, описывающий процесс бронирования рейса пользователем,
 * расчёта доступного времени оплаты бронирования рейса в зависимости от выбранного пользователем метода оплаты.
 */
@Entity
@Table(name = "Booking")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class Booking {

    /**
     * Уникальный идентификатор сущности Booking - забронированных билетов на рейс пассажира.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Забронированный пассажиром билет на рейс depart/from-to, выбранных из предоставленных SearchResult рейсов.
     * Выбирается обязательно (non-null).
     */
    @NonNull
    @OneToOne
    private Ticket departTicket;

    /**
     * Метод оплаты билета пассажиром: "оплатить позже" и "оплатить сейчас".
     * "Оплатить сейчас": выбираются способы оплаты: карта, наличными в терминалах, интернет-банкинг, безналичная.
     * "Оплатить позже" - позже надо выбрать способ оплаты, которым позже будет совершена оплата.
     * Влияет на время, которое даётся пассажиру на оплату рейса в зависимости от выбранного метода.
     */
    @Nullable
    @Column(name = "payment_method")
    private String paymentMethod;

    /**
     * Пассажир, забронировавший рейс.
     */
    @Nullable
    @OneToOne
    private Passenger passenger;

    /**
     * Статус бронирования.
     * "IN_PROGRESS" - процесс бронирования рейса пассажиром. Значение по умолчанию при инициации бронирования.
     * "PAYMENT" - происходит оплата бронирования каким-либо способом оплаты.
     * "PAID" - бронирование оплачено.
     * "BOOKED" - покупка бронирования забронирована ("оплата позже")
     * "CANCELLED" - бронирование отменено (пассажиром, компанией); в зависимости от ситуации:
     * 1) отмена за 24 часа до вылета - полный возврат;
     * 2) https://www.s7.ru/ru/fares/ - как пример для остальных случаев.
     *
     * Влияет на время, которое даётся пассажиру на оплату рейса в зависимости от выбранного статуса.
     */
    @NonNull
    @Column(name = "status")
    @Value("IN_PROGRESS")
    private String status;


    /**
     * Категория рейса.
     */
    @NonNull
    @Column(name = "category")
    private String category;

    /**
     * Уникальный номер бронирования.
     */
    @Nullable
    @Column(name = "hold_number", unique = true)
    private Long holdNumber;

    /**
     * Время начала процесса покупки бронирования рейса пользователем
     * (событие "нажатие на кнопку 'перейти к оплате'" из списка рейсов, предоставленных SearchResult),
     * нужное для высчитывания количества времени, отведённого на покупку рейса пассажиром.
     * Если paymentMethod - "оплатить позже", то даётся время:
     * 1) +3 часа от времени в этом поле, если до вылета больше 24 часов;
     * 2) +1 час от времени в этом поле, если до вылета меньше 24 часов.
     * Иначе, в случае "оплатить сейчас" каким-либо способом оплаты, время равно +1 часу от времени в этом поле.
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    @Nullable
    private LocalDateTime initialBookingDateTime;


}
