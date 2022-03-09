package app.entities.ticket;

import app.entities.route.Route;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ticket")
@Component
@Builder
public class Ticket {
    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * связь с маршрут id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private Route route;

    /**
     * самолет
     */
    @NonNull
    private String aircraft;


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
     * дата прилета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate arrivalDate;

    /**
     * время прилета
     */
    @DateTimeFormat(pattern = "hh:mm")
    @JsonFormat(pattern = "hh:mm")
    private LocalTime arrivalTime;


    /**
     * стоимость билета
     */
    @NonNull
    private Long ticketPrice;

    /**
     * номер кресла
     */
    @NonNull
    private String seat;

}
