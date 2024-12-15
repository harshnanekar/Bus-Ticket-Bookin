package springs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springs.dto.BusMaster;

@Repository
public interface BusMasterDao extends JpaRepository<BusMaster, Integer> {

    @Query("SELECT b FROM BusMaster b WHERE id = :busId AND active = TRUE")
    BusMaster findByIdActive(@Param("busId") int busId);

    @Query("SELECT b FROM BusMaster b WHERE active = TRUE")
    List<BusMaster> findAllByActive();


}

