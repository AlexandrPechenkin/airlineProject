package app.services.route;

import app.entities.route.Route;

import java.util.List;

public interface RouteService {
    List<Route> getAllRoutes();

    void createRoute(Route route);

    void removeRoute(Route route);

    Route getRouteById(Long id);
}
