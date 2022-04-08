package app.entities.dtos;

import app.entities.DestinationResource;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Data
@ApiModel
public class SearchResultDTO {
    private long id;

    @Nullable
    private Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> departFlights;

    @Nullable
    private Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> returnFlights;

    @Nullable
    private String message;
}
