package app.entities;

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

    @ManyToOne
    SearchResult searchResult;

    @Column(name = "number_of_steps")
    private Integer numberOfSteps;

    @OneToOne
    @NonNull
    private DestinationResource destinationResource;

    @ManyToMany
    private List<FlightListWrapper> flights;
}
