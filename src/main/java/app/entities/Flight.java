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
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDate;

    /**
     * время вылета
     */
    @DateTimeFormat(pattern = "HH:mm")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    /**
     * дата прилета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime arrivalDateTime;

    /**
     * место вылета
     * В БУДУЩЕМ УДАЛИТЬ
     */
    @NonNull
    private String destinationFrom;

    /**
     * место прилета
     * В БУДУЩЕМ УДАЛИТЬ
     */
    @NonNull
    private String destinationTo;

    /**
     * Enum со статусами полета - "По плану", "Задержан", "Отменён".
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;
}
