package app.entities.ticket;

import app.entities.flight.Flight;
import lombok.*;
import org.springframework.lang.Nullable;
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
     * пассажир
     */
//    @NonNull
//    private Passenger passenger;

    /**
     * номер кресла
     */
    @Nullable
    private String seat;

    /**
     * номер бронирования
     */
    @Nullable
    private Long holdNumber;

    /**
     * цена билета
     */
    @NonNull
    private Long price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id")
    private Flight flight;

}
