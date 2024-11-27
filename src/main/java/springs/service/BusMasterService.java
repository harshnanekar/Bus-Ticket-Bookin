package springs.service;

import java.lang.module.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.HibernateUtils;
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
import springs.dto.BusTypes;
import springs.exceptions.DbExceptionHandler;
import springs.repository.BusMasterDao;

@Service
public class BusMasterService {

    @Autowired
    private BusMasterDao dao;

    @Autowired
    private EntityManager entity;

    public Page<BusMaster> fetchBusMastersService(int page, int size, String search) {
        try {
            String searchValue = search.equals("null") ? null : search;
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

            CriteriaBuilder cr = entity.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = cr.createQuery(Object[].class);
            Root<BusMaster> root = criteriaQuery.from(BusMaster.class);

            Join<BusMaster, BusTypes> join = root.join("bus_type_lid", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cr.and(
                    cr.equal(root.get("active"), true),
                    cr.equal(join.get("active"), true)));

            if (searchValue != null) {
                predicates.add(cr.like(root.get("bus_name"), "%" + searchValue + "%"));
            }

            criteriaQuery.where(predicates.toArray(new Predicate[0]));

            criteriaQuery.multiselect(root.get("id"), root.get("bus_name"), join.get("bus_type"));

            criteriaQuery.orderBy(cr.desc(root.get("id")));

            TypedQuery<Object[]> typeQuery = entity.createQuery(criteriaQuery);
            typeQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            typeQuery.setMaxResults(pageable.getPageSize());

            List<Object[]> resultList = typeQuery.getResultList();

            List<BusMaster> dtoList = new ArrayList<>();
            for (Object[] result : resultList) {
                System.out.println("reult " + result.toString());
                int id = (int) result[0];
                String busName = (String) result[1];
                String busTypeName = (String) result[2];

                dtoList.add(new BusMaster(id, busName, busTypeName));
            }

            return new PageImpl<>(dtoList, pageable, resultList.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Buses Not Found");
        }
    }
}
