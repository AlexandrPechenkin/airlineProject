package app.repositories;

import app.entities.DestinationResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationResourceRepository extends JpaRepository<DestinationResource, Long> {
    @Query(value = "SELECT resource FROM DestinationResource resource " +
            "WHERE resource.city LIKE CONCAT('%',:cityName,'%')")
    List<DestinationResource> findByCity(@Param("cityName") String cityName);

    // пока подразумевается, что поиск всегда ведётся по перечислениям кодов аэропортов, по которым
    // всегда доступны данные
    @Query(nativeQuery = true, value = "SELECT * FROM destination_resource resource " +
            "WHERE base_code = :code ORDER BY id LIMIT 1")
    DestinationResource findByBaseAirportCode(@Param("code") String baseAirportCode);
}
