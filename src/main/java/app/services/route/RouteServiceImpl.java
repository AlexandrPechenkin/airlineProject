package app.services.route;

import app.entities.route.Route;
import app.repositories.route.RouteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    /**
     * создание маршрута
     */
    @Transactional
    @Override
    public void createRoute(Route route) {
        routeRepository.save(route);
    }

    /**
     * удаление маршрута
     */
    @Transactional
    @Override
    public void removeRoute(Route route) {
        routeRepository.delete(route);
    }

    /**
     * получение маршрута по id
     */
    @Transactional
    @Override
    public Route getRouteById(Long id) {
        return routeRepository.getById(id);
    }
}
