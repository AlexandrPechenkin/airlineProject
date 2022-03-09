package app.services.route;

import app.entities.route.Route;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    RouteService routeService;

    @Override
    public List<Route> getAllRoutes() {
        return routeService.getAllRoutes();
    }

    @Override
    public void createRoute(Route route) {
        routeService.createRoute(route);
    }

    @Override
    public void removeRoute() {
        routeService.removeRoute();
    }

    @Override
    public Route getRouteById(Long id) {
        return routeService.getRouteById(id);
    }
}
