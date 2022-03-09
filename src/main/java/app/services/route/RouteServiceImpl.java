package app.services.route;

import app.entities.route.Route;
import app.repositories.route.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    /**
     * получение всех маршрутов
     */
    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    /**
     * создание маршрута
     */
    @Override
    public void createRoute(Route route) {
        routeRepository.save(route);
    }

    /**
     * удаление маршрута
     */
    @Override
    public void removeRoute(Route route) {
        routeRepository.delete(route);
    }

    /**
     * получение маршрута по id
     */
    @Override
    public Route getRouteById(Long id) {
        return routeRepository.getById(id);
    }
}
