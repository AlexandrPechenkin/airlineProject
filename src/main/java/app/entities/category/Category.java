package app.entities.category;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Класс категории конкретного места на рейсе
 * @author Alexandr Pechenkin
 * @version 1.1
 */

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Category")
@Component
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    /** Поле ID */
    private Long id;

    @NonNull
    /** Поле категории */
    private String category;
}
