package app.entities.aircraft;

import app.entities.Seat;
import app.entities.category.Category;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Aircraft")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hull_number")
    private String hullNumber;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @OneToMany(targetEntity = Seat.class)
    private Map<Category, List<Seat>> cabinConfig;

    @Column(name = "`range`")
    private Integer range;
}
