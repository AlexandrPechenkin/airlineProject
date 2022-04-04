package app.util;

import app.entities.Category;
import app.entities.Route;
import app.services.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final CategoryService categoryService;


    public Route getRouteByJSON(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

        LocalDate departureDate = LocalDate.parse(jsonObject.get("departureDate").toString());
        LocalDate departureDateOfReturn = LocalDate.parse(jsonObject.get("departureDateOfReturn").toString());
        int numberOfSeats = Integer.parseInt(jsonObject.get("numberOfSeats").toString());
        String from = jsonObject.get("from").toString();
        String to = jsonObject.get("to").toString();
        Category category = categoryService.getByCategoryByString(jsonObject.get("category").toString());

        return new Route(departureDate, departureDateOfReturn, numberOfSeats, category, from, to);
    }
}
