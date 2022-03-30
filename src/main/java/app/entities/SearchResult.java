package app.entities;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

/**
 * Класс, нужный для предоставления результатов поиска доступных рейсов для пользователя
 * и аналитики.
 */
@Entity
@Table(name = "Search_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class SearchResult {

    /**
     * уникальный идентификатор результата поиска рейсов
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * список рейсов from-to
     */
    @Nullable
    @OneToMany
//    @JoinColumn(name = "search_result_depart")
    private List<Flight> departFlights;

    /**
     * список рейсов to-from
     */
    @Nullable
    @OneToMany
//    @JoinColumn(name = "search_result_return")
    private List<Flight> returnFlights;

    /**
     * Сообщение, которое возникло вследствие ошибки/дачи дополнительной информации пользователю.
     */
    @Nullable
    @Column(name = "message")
    private String message;
}
