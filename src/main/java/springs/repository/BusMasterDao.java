package springs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springs.dto.BusMaster;

@Repository
public interface BusMasterDao extends JpaRepository<BusMaster, Integer> {


}
