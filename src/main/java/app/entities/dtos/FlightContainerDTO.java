package app.entities.dtos;

import app.entities.DestinationResource;
import app.entities.FlightListWrapper;
import app.entities.SearchResult;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Jacksonized
@ApiModel
public class FlightContainerDTO {
    private long id;
    private SearchResult searchResultDTO;
    private Integer numberOfSteps;
    private DestinationResource destinationResource;
    private List<FlightListWrapper> flights;
}
