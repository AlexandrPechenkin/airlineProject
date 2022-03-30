package app.repositories;

import app.entities.Destination;
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
            "and f.to.city like  concat('%', :cityTo, '%') " +
            "and f.departureDate like concat('%', :departureDate, '%')")
    List<Flight> findFlights(@Param("cityFrom") String from, @Param("cityTo") String to, LocalDate departureDate);


//    @Query(value = "select f from Flight f where f.from.city like concat('%',:cityFrom,'%')" +
//            "and f.to.city like concat('%',:cityTo,'%')" )
//    List<Flight> findFlightsByCitiesAndAirportCode(@Param("cityFrom") String cityFrom, @Param("cityTo") String cityTo,
//                                                   @Param("airportCode") String airportCode,
//                                                   @Param("localDate") LocalDate departureDate);

//    @Query(value = "select f from Flight f where f.from.city like concat('%', :from, '%')" +
//            "and f.to.city like  concat('%', :to, '%') " +
//            "and f.departureDate like concat('%', :departureDate, '%')")
//    List<Flight> findFlights(@Param("from") Destination from, @Param("to") Destination to,
//                             @Param("departureDate") LocalDate departureDate);

}
