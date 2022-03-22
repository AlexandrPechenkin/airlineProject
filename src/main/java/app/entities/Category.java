package app.entities;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Класс категории конкретного места на рейсе
 *
 * @author Alexandr Pechenkin
 * @version 1.1
 */

@Data
@Builder
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
