package app.services.route;

import app.entities.route.Route;
import app.repositories.route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public void createRoute(Route route) {
        routeRepository.save(route);
    }

    @Override
    public void removeRoute(Route route) {
        routeRepository.delete(route);
    }

    @Override
    public Route getRouteById(Long id) {
        return routeRepository.getById(id);
    }
}
