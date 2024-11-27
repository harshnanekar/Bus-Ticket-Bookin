package springs.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import springs.dto.BusTypes;

@Repository
public interface BusTypesDao extends JpaRepository<BusTypes, Integer> {

    @Query("SELECT b FROM BusTypes b WHERE (:search IS NULL OR b.bus_type LIKE %:search%) AND b.active = TRUE")
    Page<BusTypes> fetchBusTypesDao(Pageable pageable, String search);
}
