package app.entities.booking;

import app.entities.flight.Flight;
import app.entities.passenger.Passenger;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
     * Уникальный идентификатор сущности Booking - забронированных рейсов пассажира
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Список забронированных пассажиром рейсов depart/from-to, выбранных из предоставленных SearchResult рейсов.
     * Один пассажир может бронировать несколько depart-рейсов.
     * Выбирается обязательно (non-null).
     */
    @NonNull
    @OneToMany(targetEntity=Flight.class,cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Map<Passenger, List<Flight>> passengerFlightsDepart;

    /**
     * Список забронированных пассажиром рейсов return/to-from, выбранных из предоставленных SearchResult рейсов.
     * Один пассажир может бронировать несколько return-рейсов.
     * Может быть не выбран (nullable).
     */
    @Nullable
    @OneToMany(targetEntity = Flight.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Map<Passenger, List<Flight>> passengerFlightsReturn;

    /**
     * Метод оплаты билета пассажиром: "оплатить позже" и "оплатить сейчас".
     * "Оплатить сейчас": выбираются способы оплаты: карта, наличными в терминалах, интернет-банкинг, безналичная.
     * "Оплатить позже" - позже надо выбрать способ оплаты, которым позже будет совершена оплата.
     * Влияет на время, которое даётся пассажиру на оплату рейса в зависимости от выбранного метода.
     */
    @Column(name = "payment_method")
    private String paymentMethod;

    /**
     * Оплатил ли пользователь билет по истечении времени бронирования рейса.
     * Если не оплатил, бронирование рейса удаляется, если оплатил, то получает статус "PAID".
     * Бронирование рейса со статусом далее может изменяться в зависимости от статуса.
     */
    @NonNull
    @Column(name = "is_sold")
    private Boolean isSold;

    /**
     * Статус бронирования.
     * "IN_PROGRESS" - процесс бронирования рейса пассажиром. Значение по умолчанию при инициации бронирования.
     * "PAID" - бронирование оплачено.
     * "CANCELLED" - бронирование отменено (пассажиром, компанией); в зависимости от ситуации:
     * 1) отмена за 24 часа до вылета - полный возврат;
     * 2) https://www.s7.ru/ru/fares/ - как пример для остальных случаев.
     */
    @NonNull
    @Column(name = "status")
    private String status = "IN_PROGRESS";

    /**
     * Время начала бронирования рейса пользователем (событие "нажатие на кнопку 'выбрать'" из списка
     * рейсов, предоставленных SearchResult), нужное для высчитывания количества времени,
     * отведённого на покупку рейса пассажиром.
     * Если paymentMethod - "оплатить позже", то даётся время:
     * 1) +3 часа от времени в этом поле, если до вылета больше 24 часов;
     * 2) +1 час от времени в этом поле, если до вылета меньше 24 часов.
     * Иначе, в случае "оплатить сейчас" каким-либо способом оплаты, время равно +1 часу от времени в этом поле.
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime initialBookingDateTime;


}