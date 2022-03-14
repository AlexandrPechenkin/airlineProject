package app.entities.category;

import app.entities.seat.Seat;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Класс категории конкретного места на рейсе
 *
 * @author Alexandr Pechenkin
 * @version 1.1
 */

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
@Component
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    /**
     * ID
     */
    private Long id;

    /**
     * Имя категории
     */
    @NonNull
    private String category;

}
