package springs.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springs.dto.BusTypes;

@Repository
public interface BusTypesDao extends JpaRepository<BusTypes, Integer> {

    @Query("SELECT b FROM BusTypes b WHERE (:search IS NULL OR b.bus_type LIKE %:search%) AND b.active = TRUE")
    Page<BusTypes> fetchBusTypesDao(Pageable pageable, String search);

    @Query(value = "SELECT b.id,b.bus_type,b.active,b.created_date,b.created_by,b.modified_date,b.modified_by FROM bus_types b WHERE b.active = TRUE", nativeQuery = true)
    List<BusTypes> findAllByActive();
}
