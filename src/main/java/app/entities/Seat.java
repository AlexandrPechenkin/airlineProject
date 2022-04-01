package app.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;


/**
 * Класс места на рейсе
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */
@Builder
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
@Component
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер места
     */
    @NonNull
    private String seatNumber;

    /**
     * Стоимость
     */
    @NonNull
    private Integer fare;

    /**
     * Зарегистрирован ли пассажир
     */
    @NonNull
    private Boolean isRegistered;

    /**
     * Продано ли место
     */
    @NonNull
    private Boolean isSold;
//
//    /**
//     * Рейс
//     */
//    @NonNull
//    @ManyToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "flight_id")
//    private Flight flight;

}
