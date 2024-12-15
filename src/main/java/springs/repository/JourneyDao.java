package springs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springs.dto.Journey;

@Repository
public interface JourneyDao extends JpaRepository<Journey,Integer>{
    
}
