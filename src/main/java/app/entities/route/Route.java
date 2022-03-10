package app.entities.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Route")
@Component
@Builder
public class Route {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * место вылета
     */
    @NonNull
    private String destinationFrom;

    /**
     * место прилета
     */
    @NonNull
    private String destinationTo;

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
     * количество сидений
     */
    @NonNull
    private Integer numberOfSeats;


    /**
     * категория билета
     */
//    @NonNull
//    private Category category;
}
