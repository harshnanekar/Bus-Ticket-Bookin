package springs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springs.dto.Routes;

@Repository
public interface RoutesDao extends JpaRepository<Routes,Integer>{

    @Query("SELECT r FROM Routes r WHERE (:search IS NULL OR r.route LIKE %:search%) AND r.active = TRUE")
    Page<Routes> findByRoutes(@Param("search") String search, Pageable pageable);

}

