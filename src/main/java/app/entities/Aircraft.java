package app.entities;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Класс Aircraft отвечает за состояние воздушного судна
 * @author Александр Данилов
 * @version 0.1
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aircrafts")
public class Aircraft {
    /**
     * Уникальный ID воздушного судна
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Брэнд воздушного судна
     */
    private String brand;

    /**
     * Модель воздушного судна
     */
    private String model;

    /**
     * Бортовой номер воздушного судна
     */
    private String boardNumber;

    /**
     * Год выпуска воздушного судна
     */
    private LocalDate productionYear;

    /**
     * Дальность полета воздушного судна
     */
    private Integer flyingRange;

    /**
     * Категория {@link Category} мест в салоне воздушного судна
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Category> categories;

    /**
     * Места самолёта по категориям
     */
    @Nullable
    @OneToMany(targetEntity = Seat.class)
    private Map<Category, List<Seat>> seatsByCategory;
}
