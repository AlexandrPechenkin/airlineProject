package app.entities;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Класс категории конкретного места на рейсе
 * @Autor Alexandr Pechenkin
 * @version 1.0
 */

@Data
@Entity
@Table(name="Category")
@Component
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    /** Поле ID */
    private Long id;

    /** Поле категории */
    private String category;

    /** Конструктор с категорией */
    public Category(String category) {
        this.category = category;
    }

    /** Конструктор пустой */
    public Category() {
    }
}
