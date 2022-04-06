package app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="flight")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата вылета
     */
    private LocalDate departureDate;

    /**
     * время вылета
     */
    private LocalTime departureTime;

    /**
     * дата прилета
     */
    private LocalDateTime arrivalDateTime;

    /**
     * место вылета
     * В БУДУЩЕМ УДАЛИТЬ
     */
    private String destinationFrom;

    /**
     * место прилета
     * В БУДУЩЕМ УДАЛИТЬ
     */
    private String destinationTo;

    /**
     * Enum со статусами полета - "По плану", "Задержан", "Отменён".
     */
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;
}
