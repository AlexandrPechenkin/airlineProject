package app.entities.flight;


import app.entities.route.Route;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Flight")
@Component
@Builder
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
     * связь с маршрут id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private Route route;
}
