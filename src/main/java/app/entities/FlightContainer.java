package app.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "flight_container")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties(value = {"departFlights", "returnFlights"})
    @ManyToOne
    SearchResult searchResult;

    @Column(name = "number_of_steps")
    private Integer numberOfSteps;

    @OneToOne
    @NonNull
    private DestinationResource destinationResource;

    @Column(name = "category")
    private String category;

    @ManyToMany
    @JsonIgnoreProperties("flightContainer")
    private List<FlightListWrapper> flights;
}
