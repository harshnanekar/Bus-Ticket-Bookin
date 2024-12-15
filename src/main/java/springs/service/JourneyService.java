package springs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import springs.dto.BusMaster;
import springs.dto.Journey;
import springs.dto.Routes;
import springs.exceptions.DbExceptionHandler;
import springs.repository.BusMasterDao;

@Service
public class JourneyService {

    @Autowired
    private EntityManager entity;

    public Page<Journey> fetchJourneyDetails(int page, int size, String search) {
        try {

            String searchValue = search.equals("null") || search.equals("") ? null : search;
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

            CriteriaBuilder cb = entity.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = cb.createQuery(Object[].class);
            Root<Journey> root = criteriaQuery.from(Journey.class);

            Join<Journey, BusMaster> join = root.join("bus_lid", JoinType.INNER);
            Join<Journey, Routes> arrivalJoin = root.join("arrival_location", JoinType.INNER);
            Join<Journey, Routes> deparJoin = root.join("departure_location", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.and(
                    cb.equal(root.get("active"), true),
                    cb.equal(join.get("active"), true),
                    cb.equal(arrivalJoin.get("active"), true),
                    cb.equal(deparJoin.get("active"), true)));

            if (search != null) {
                predicates.add(
                        cb.or(
                                cb.like(join.get("bus_name"), "%" + searchValue + "%"),
                                cb.like(arrivalJoin.get("route"), "%" + searchValue + "%"),
                                cb.like(deparJoin.get("route"), "%" + searchValue + "%"),
                                cb.equal(root.get("fare"), searchValue)));
            }

            criteriaQuery.multiselect(root.get("fare"), join.get("bus_name"), arrivalJoin.get("route"),
                    deparJoin.get("route"), root.get("id"));

            TypedQuery<Object[]> typeQuery = entity.createQuery(criteriaQuery);
            typeQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            typeQuery.setMaxResults(pageable.getPageSize());

            List<Object[]> resultList = typeQuery.getResultList();

            List<Journey> dtoList = new ArrayList<>();
            for (Object[] result : resultList) {
                System.out.println("reult " + result.toString());
                int fare = (int) result[0];
                String busName = (String) result[1];
                String arrivalLocation = (String) result[2];
                String departureLocation = (String) result[3];
                int id = (int) result[4];

                Journey journeyObj = new Journey();
                journeyObj.setFare(fare);
                journeyObj.setBusName(busName);
                journeyObj.setArrivalLocation(arrivalLocation);
                journeyObj.setDepartureLocation(departureLocation);
                journeyObj.setId(id);

                dtoList.add(journeyObj);
            }

            return new PageImpl<>(dtoList, pageable, resultList.size());

        } catch (Exception e) {
            e.printStackTrace();
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Journey Details Not Found");
        }
    }
}
