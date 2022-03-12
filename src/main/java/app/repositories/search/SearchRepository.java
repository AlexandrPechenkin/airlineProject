package app.repositories.search;

import app.entities.route.Route;
import app.entities.search.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

    @Query(value = "select s from Search s where (s.id like concat('%', :id, '%'))")
    List<Route> findFlights(Long id);

}
