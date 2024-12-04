package springs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import springs.dto.BusMaster;
import springs.dto.BusTypes;
import springs.dto.Seats;
import springs.exceptions.DbExceptionHandler;
import springs.repository.BusDao;
import springs.repository.BusMasterDao;
import springs.repository.BusTypesDao;
import springs.utils.SeatPosition;

@Service
public class BusMasterService {

    @Autowired
    private EntityManager entity;

    @Autowired
    private BusDao busDao;

    @Autowired
    private HttpSession session;

    @Autowired
    private BusMasterDao dao;

    public Page<BusMaster> fetchBusMastersService(int page, int size, String search) {
        try {
            String searchValue = search.equals("null") || search.equals("") ? null : search;
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

                BusMaster busMasterObj = new BusMaster();
                busMasterObj.setId(id);
                busMasterObj.setBus_name(busName);
                busMasterObj.setBus_type(busTypeName);
                dtoList.add(busMasterObj);
            }

            return new PageImpl<>(dtoList, pageable, resultList.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Buses Not Found");
        }
    }

    @Transactional
    public Map<String, String> submitBusService(BusMaster bus) {
        try {
            String createdBy = (String) session.getAttribute("username");

            bus.setCreated_by(createdBy);
            int busId = busDao.createBus(bus);

            int seatRowCount = bus.getTotal_rows() / 2;
            int seatMiddleCount = seatRowCount + 1;
            int seatIndex = 1;
            SeatPosition seatPosition;

            List<Seats> seats = new ArrayList<>();

            for (int i = bus.getTotal_rows(); i >= 1; i--) {

                if (seatMiddleCount == i) {
                    Seats seatObj = new Seats();
                    seatObj.setBusId(busId);
                    seatObj.setSeat_position(SeatPosition.MIDDLE);
                    seatObj.setSeat_number(seatIndex);
                    seatObj.setCreated_by(createdBy);

                    seats.add(seatObj);
                    seatIndex++;
                    continue;
                }

                seatPosition = getSeatPosition(seatRowCount, i);

                if (i % 2 == 0) {
                    for (int j = 1; j <= bus.getSeats_per_row(); j++) {

                        Seats seatObj = new Seats();
                        seatObj.setBusId(busId);
                        seatObj.setSeat_position(seatPosition);
                        seatObj.setSeat_number(seatIndex);
                        seatObj.setCreated_by(createdBy);

                        seats.add(seatObj);

                        seatIndex++;
                    }
                } else {
                    for (int j = bus.getSeats_per_row(); j >= 1; j--) {
                        Seats seatObj = new Seats();
                        seatObj.setBusId(busId);
                        seatObj.setSeat_position(seatPosition);
                        seatObj.setSeat_number(seatIndex);
                        seatObj.setCreated_by(createdBy);

                        seats.add(seatObj);

                        seatIndex++;
                    }
                }
            }

            int[] seatInsert = busDao.createBusSeats(seats);
            if (seatInsert.length == 0) {
                throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Creating Bus Seats");
            }

            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Bus Created Successfully");
            return responseJson;

        } catch (Exception e) {
            e.printStackTrace();
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Creating Bus");
        }
    }

    private SeatPosition getSeatPosition(int rowCount, int row) {
        if (rowCount >= row) {
            return SeatPosition.RIGHT;
        } else {
            return SeatPosition.LEFT;
        }
    }

    public BusMaster fetchBusService(int id) {
        try {

            BusMaster bus = dao.findById(id)
                    .orElseThrow(() -> new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Not Found"));
    
            BusMaster busObj  = new BusMaster();
            busObj.setId(bus.getId());
            busObj.setBus_name(bus.getBus_name());
            busObj.setBus_type_id(bus.getBus_type_lid().getId());
            return busObj;

        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Not Found");
        }
    }

   

}
