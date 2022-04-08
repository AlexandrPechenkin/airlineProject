package app.entities;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.*;
import java.util.List;
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
    @OneToMany(targetEntity = Flight.class)
    private Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> departFlights;

    /**
     * список рейсов to-from
     */
    @Nullable
    @OneToMany(targetEntity = Flight.class)
    private Map<Integer, MultiValueMap<DestinationResource, List<Flight>>> returnFlights;
//    @Nullable
//    @OneToMany
//    private List<Flight> departFlights;
//
//    @Nullable
//    @OneToMany
//    private List<Flight> returnFlights;

    /**
     * Сообщение, которое возникло вследствие ошибки/дачи дополнительной информации пользователю.
     */
    @Nullable
    @Column(name = "message")
    private String message;
}
