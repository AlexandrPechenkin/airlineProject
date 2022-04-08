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
//    private List<FlightDTO> departureFlights;
    private Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> departureFlightsMap;

    @Nullable
//    private List<FlightDTO> returnFlights;
    private Map<Integer, MultiValueMap<DestinationResource, List<FlightDTO>>> returnFlightsMap;
}
