package app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
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
     * Enum со статусами полета - "По плану", "Задержан", "Отменён".
     */
    @NonNull
    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;

    /**
     * Destination from
     */
    @NonNull
    @OneToOne
    private Destination from;

    /**
     * Destination to
     */
    @NonNull
    @OneToOne
    private Destination to;

    /**
     * самолёт
     */
    @Nullable
    @ManyToOne
    private Aircraft aircraft;
}
