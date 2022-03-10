package app.entities.ticket;

import app.entities.flight.Flight;
import app.entities.route.Route;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Random;

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
     * пассажир
     */
//    @NonNull
//    private Passenger passenger;

    /**
     * номер кресла
     */
    @NonNull
    private String seat;

    /**
     * номер бронирования
     */
    @NonNull
    private Long holdNumber;

    /**
     * связь с перелетом id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private Flight flight;


}
