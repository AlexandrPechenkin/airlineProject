package app.entities.ticket;

import app.entities.flight.Flight;
import app.entities.route.Route;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

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
     * самолет
     */
    @NonNull
    private String aircraft;

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

    /**
     * связь с перелетом id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
