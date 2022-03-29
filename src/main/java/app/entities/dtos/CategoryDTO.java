package app.entities.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Transfer Object - передающий данные из Category наружу.
 *
 * @author Alexandr Pechenkin
 * @version 1.0
 */
@Data
public class CategoryDTO {
    private long id;

    @NotNull(message = "Имя категории не может быть пустым")
    private String category;

    private List<SeatDTO> seats;

}
