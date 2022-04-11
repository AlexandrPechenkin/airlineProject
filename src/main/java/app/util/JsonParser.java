package app.util;

import app.entities.Category;
import app.entities.Destination;
import app.entities.Route;
import app.services.interfaces.CategoryService;
import app.services.interfaces.DestinationService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final CategoryService categoryService;
    private final DestinationService destinationService;


    /**
     * метод для парсинга из JSON с фронта в объект Route
     * @param response
     * @return
     * @throws ParseException
     */
    public Route getFlightPropertiesByJSONWithCityNames(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

        LocalDate departureDate = LocalDate.parse(jsonObject.get("departureDate").toString());
        LocalDate departureDateOfReturn = LocalDate.parse(jsonObject.get("departureDateOfReturn").toString());
        int numberOfSeats = Integer.parseInt(jsonObject.get("numberOfSeats").toString());
        String from = jsonObject.get("from").toString();
        List<Destination> fromDestList = destinationService.getDestinationListByCity(from);
        String to = jsonObject.get("to").toString();
        List<Destination> toDestList = destinationService.getDestinationListByCity(to);
        Category category = categoryService.getByCategoryByString(jsonObject.get("category").toString());

        Route routeWithFlightOptions = new Route();
        if (!fromDestList.isEmpty() && !toDestList.isEmpty()) {
            routeWithFlightOptions.setDepartureDate(departureDate);
            routeWithFlightOptions.setReturnDate(departureDateOfReturn);
            routeWithFlightOptions.setNumberOfSeats(numberOfSeats);
            routeWithFlightOptions.setCategory(category);
            routeWithFlightOptions.setFrom(fromDestList.get(0));
            routeWithFlightOptions.setTo(toDestList.get(0));
        }
        return routeWithFlightOptions;
    }
}
