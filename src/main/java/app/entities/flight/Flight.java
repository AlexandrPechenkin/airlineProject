package app.entities.flight;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

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

}
