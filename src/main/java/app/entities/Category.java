package app.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Класс категории конкретного места на рейсе
 *
 * @author Alexandr Pechenkin
 * @version 1.1
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя категории
     */
    @NonNull
    private String category;

    /**
     * Места {@link Seat} в воздушном судне которые относятся к разным категориям
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Seat> seats;

}
