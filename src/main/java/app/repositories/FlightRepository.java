package app.repositories;

import app.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    /**
     * поиск по месту вылета, месту прилета и дате вылета
     *
     * @param from          место вылета
     * @param to            место прилета
     * @param departureDate дата вылета
     * @return
     */
    @Query(value = "select f from Flight f where (f.from.city like concat('%', :cityFrom, '%')) " +
            "and f.to.city like concat('%', :cityTo, '%') " +
            "and f.departureDate like concat('%', :date, '%')")
    List<Flight> findFlights(@Param("cityFrom") String from, @Param("cityTo") String to,
                             @Param("date") LocalDate departureDate);





    /**
     * поиск по месту вылета, месту прилета и дате вылета
     *
     * @param from          аэропорт вылета
     * @param to            аэропорт прилета
     * @param departureDate дата вылета
     * @return
     */
    @Query(value = "select f from Flight f where (f.from.airportCode like concat('%', :cityFrom, '%')) " +
            "and f.to.airportCode like concat('%', :cityTo, '%') " +
            "and f.departureDate like concat('%', :date, '%')")
    List<Flight> findFlightsByDestination(@Param("cityFrom") String from, @Param("cityTo") String to,
                             @Param("date") LocalDate departureDate);





    @Query(value = "select f from Flight f where f.departureDate > :date " +
            "and f.from.city like concat('%',:cityFrom,'%') " +
            "and f.to.city like concat('%',:cityTo,'%')")
    List<Flight> findAllWithDepartureDateAfter(@Param("cityFrom") String cityFrom,
                                               @Param("cityTo") String cityTo,
                                               @Param("date") LocalDate date);
}
