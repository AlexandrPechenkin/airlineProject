package app.util;

import app.entities.*;
import app.services.interfaces.CategoryService;
import app.services.interfaces.FlightContainerService;
import app.services.interfaces.SearchResultService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Component
public class JsonFlightContainerParser {

    private final CategoryService categoryService;
    private final SearchResultService searchResultService;
    private final FlightContainerService flightContainerService;

    public FlightContainer getFlightContainerForSetBookingPropertiesByJSONFromSearchResult(String response)
            throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(response);

        Optional<FlightContainer> containerOpt = flightContainerService.findById(
                Long.parseLong(jsonObject.get("flight_container_id").toString()));
        FlightContainer flightContainer = null;
        if (containerOpt.isPresent()) {
            flightContainer = containerOpt.get();
            flightContainer.setCategory(jsonObject.get("category").toString());
        }
        return flightContainer;
    }
}
