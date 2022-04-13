package app.services.impl;

import app.entities.Flight;
import app.repositories.FlightRepository;
import app.services.interfaces.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;


    /**
     * получение всех перелетов
     */
    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    /**
     * создание перелета
     * @return
     */
    @Override
    public Flight createOrUpdateFlight(Flight flight) {
        flightRepository.save(flight);
        return flight;
    }

    /**
     * удаление перелета
     */
    @Override
    public void removeFlight(Flight flight) {
        flightRepository.delete(flight);
    }

    /**
     * получение перелета по id
     */
    @Override
    public Optional<Flight> findById(Long id) {

        return flightRepository.findById(id);
    }

    /**
     * поиск перелета с пересадками по маршруту дате вылета
     *
     * @param from          место вылета
     * @param to            место прилета
     * @param departureDate дата вылета
     * @return
     */
    @Override
    public List<Flight> findFlights(String from, String to, LocalDate departureDate) {
        return flightRepository.findFlights(from, to, departureDate);
    }

    /**
     * поиск перелета с пересадками по маршруту дате вылета
     *
     * @param from          аэропорт вылета
     * @param to            аэропорт прилета
     * @param departureDate дата вылета
     * @return
     */
    @Override
    public List<Flight> findFlightsByDestination(String from, String to, LocalDate departureDate) {
        return flightRepository.findFlightsByDestination(from, to, departureDate);
    }


    @Override
    public List<Flight> findAllWithDepartureDateAfter(String cityFrom, String cityTo, LocalDate date) {
        return flightRepository.findAllWithDepartureDateAfter(cityFrom, cityTo, date);
    }
}
