package app.util;

import app.entities.Category;
import app.entities.Route;
import app.services.interfaces.CategoryService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;

public class JsonParser {

    CategoryService categoryService;

    public Route getRouteByJSON(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(response);

        Long id = Long.parseLong(jsonObject.get("id").toString());
        LocalDate departureDate = LocalDate.parse(jsonObject.get("departureDate").toString());
        LocalDate departureDateOfReturn = LocalDate.parse(jsonObject.get("departureDateOfReturn").toString());
        int numberOfSeats = Integer.parseInt(jsonObject.get("numberOfSeats").toString());
        String from = jsonObject.get("from").toString();
        String to = jsonObject.get("to").toString();
        Category category = categoryService.getByCategoryByString(jsonObject.get("category").toString());

        return new Route(id, departureDate, departureDateOfReturn, numberOfSeats, category, from, to);
    }
}
