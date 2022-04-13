package app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Map;

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
    @JsonIgnoreProperties("searchResult")
    @OneToMany(mappedBy = "searchResult", targetEntity = FlightContainer.class, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "numberOfSteps")
    private Map<Integer, FlightContainer> departFlights;

    /**
     * список рейсов to-from
     */
    @Nullable
    @JsonIgnoreProperties("searchResult")
    @OneToMany(mappedBy = "searchResult", targetEntity = FlightContainer.class, fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "numberOfSteps")
    private Map<Integer, FlightContainer> returnFlights;

    /**
     * Сообщение, которое возникло вследствие ошибки/дачи дополнительной информации пользователю.
     */
    @Nullable
    @Column(name = "message")
    private String message;
}
