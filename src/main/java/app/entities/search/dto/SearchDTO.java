package app.entities.search.dto;

import app.entities.route.Route;
import app.entities.search.SearchStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SearchDTO {
    private Long id;
    @NotNull(message = "Лист маршрутов не может быть пустым")
    private List<Route> routeList;
    @NotNull(message = "Статус поиска не может быть пустым")
    private SearchStatus searchStatus;
}
