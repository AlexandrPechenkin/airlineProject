package app.services.flight;

import app.entities.flight.Flight;
import app.repositories.flight.FlightRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     * получение всех перелетов
     */
    @Transactional
    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    /**
     * создание перелета
     */
    @Transactional
    @Override
    public void createOrUpdateFlight(Flight flight) {
        flightRepository.save(flight);
    }

    /**
     * удаление перелета
     */
    @Transactional
    @Override
    public void removeFlight(Flight flight) {
        flightRepository.delete(flight);
    }

    /**
     * получение перелета по id
     */
    @Transactional
    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.getById(id);
    }

    /**
     * поиск перелета с пересадками по маршруту дате вылета
     *
     * @param from          место вылета
     * @param to            место прилета
     * @param departureDate дата вылета
     * @return
     */
    @Transactional
    @Override
    public List<Flight> findFlights(String from, String to, LocalDate departureDate) {
        return flightRepository.findFlights(from, to, departureDate);
    }

}
