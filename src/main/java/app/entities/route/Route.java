package app.entities.route;


import app.entities.flight.Flight;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Route")
@Component
@Builder
public class Route {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * код маршрута
     */
    @NonNull
    private String route;

    /**
     * место вылета
     */
    @NonNull
    private String origin;

    /**
     * место прилета
     */
    @NonNull
    private String destination;

}
