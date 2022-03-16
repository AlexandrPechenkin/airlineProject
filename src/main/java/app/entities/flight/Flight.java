package app.entities.flight;

import app.entities.seat.Seat;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Builder
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fligth")
@Component
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String destinationFrom;

    @NonNull
    private String destinationTo;


//    public Flight(String destinationFrom, String destinationTo) {
//        this.destinationFrom = destinationFrom;
//        this.destinationTo = destinationTo;
//    }


}
