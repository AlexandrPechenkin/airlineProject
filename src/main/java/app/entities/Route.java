package app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Route")
@Component
@Builder
@Data
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
    private LocalDate departureDateOfReturn;


    /**
     * количество сидений
     */
    @NonNull
    private int numberOfSeats;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "search_id", nullable = false)
//    private Search search;


    /**
     * категория билета
     */
    @OneToOne
    @JoinColumn(name = "category_id")
    @NonNull
    private Category category;



    /**
     * Destination from
     */
//    @NonNull
//    private Destination from;
    private String from;

    /**
     * Destination to
     */
//    @NonNull
//    private Destination to;
    private String to;
}
