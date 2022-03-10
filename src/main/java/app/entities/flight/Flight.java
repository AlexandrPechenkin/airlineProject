package app.entities.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Flight")
@Component
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flight {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата вылета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDate;

    /**
     * время вылета
     */
    @DateTimeFormat(pattern = "hh:mm")
    @JsonFormat(pattern = "hh:mm")
    private LocalTime departureTime;

    /**
     * дата и время прилета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy hh:mm")
    @JsonFormat(pattern = "dd.MM.yyyy hh:mm")
    private LocalDateTime arrivalDateTime;

    /**
     * место вылета
     * В БУДУЩЕМ УДАЛИТЬ
     */
    @NonNull
    @Column(name = "destinationFrom")
    private String from;

    /**
     * место прилета
     * В БУДУЩЕМ УДАЛИТЬ
     */
    @NonNull
    @Column(name = "destinationTo")
    private String to;

    /**
     * Enum со статусами полета - "По плану", "Задержан", "Отменён".
     */
    @NonNull
    private FlightStatus flightStatus;

    /**
     * Destination from
     */
//    @NonNull
//    private Destination from;

    /**
     * Destination to
     */
//    @NonNull
//    private Destination to;

    /**
     * места на рейса
     */
//    @NonNull
//    private Map<Category, List<Seat>> seatsByCategory;

    /**
     * самолет
     */
//    @NonNull
//    private Aircraft aircraft;
}
