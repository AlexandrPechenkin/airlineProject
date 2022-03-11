package app.entities.route;

import app.entities.search.Search;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

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
     * дата вылета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate departureDate;

    /**
     * дата прилета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate arrivalDate;


    /**
     * количество сидений
     */
    @NonNull
    private int numberOfSeats;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "search_id", nullable = false)
    private Search search;


    /**
     * категория билета
     */
//    @NonNull
//    private Category category;

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
}
