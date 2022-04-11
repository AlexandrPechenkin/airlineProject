package app.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Route")
@Component
@Builder
@Getter
@Setter
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
    @Nullable
    private LocalDate departureDate;

    /**
     * дата прилета
     */
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Nullable
    private LocalDate returnDate;


    /**
     * количество сидений
     */
    @Nullable
    private int numberOfSeats;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "search_id", nullable = false)
    @Nullable
    private Search search;


    /**
     * категория билета
     */
    @Nullable
    @OneToOne
    private Category category;

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
}
