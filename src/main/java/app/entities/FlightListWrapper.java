package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flight_list_wrapper")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightListWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("flights")
    private FlightContainer flightContainer;

    @ManyToMany
    List<Flight> allFlightsFromToCities;
}
