package app.repositories;

import app.entities.Destination;
import app.entities.Route;
import app.entities.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

//    @Query(value = "select r from Route r where (r.from.city like concat('%', :cityFrom, '%'))")
//    List<Route> findRoutesByDestinations(@Param("cityFrom") String cityFrom, @Param("cityTo") String cityTo,
//                                         LocalDate localDate);
}
