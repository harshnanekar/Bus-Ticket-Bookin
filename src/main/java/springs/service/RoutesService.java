package springs.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import springs.dto.Routes;
import springs.exceptions.DbExceptionHandler;
import springs.repository.RoutesDao;

@Service
public class RoutesService {

    @Autowired
    private RoutesDao dao;

    @Autowired
    private HttpSession session;

    public Page<Routes> getRoutesDataService(int page, int size, String search) {
        try {
            String searchValue = search.equals("null") || search.equals("") ? null : search;
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            return dao.findByRoutes(searchValue, pageable);
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Fetching Routes");
        }
    }

    @Transactional
    public ResponseEntity<Object> submitRoutesService(Routes route) {
        String createdBy = (String) session.getAttribute("username");
        Routes routeDao = new Routes();
        routeDao.setRoute(route.getRoute());
        routeDao.setCreated_by(createdBy);
        routeDao.setCreated_date(new Date());
        routeDao.setActive(true);

        try {
            dao.save(routeDao);
            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Route Created Succesfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Creating Route");
        }
    }

    @Transactional
    public Map<String, String> deleteRouteService(int id) {
        String updatedBy = (String) session.getAttribute("username");

        try {
            Routes routeDao = dao.findById(id)
                    .orElseThrow(() -> new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Route Not Found"));
            routeDao.setActive(false);        
            routeDao.setModified_by(updatedBy);
            routeDao.setModified_date(new Date());
            dao.save(routeDao);

            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Route Deleted Succesfully");
            return responseJson;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Deleting Route");
        }
    }

    public Routes fetchRoute(int id) {
        try {
            Optional<Routes> route = dao.findById(id);
            if (route.isPresent()) {
                return route.get();
            } else {
                return new Routes();
            }

        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Routes Not Found");
        }
    }

    @Transactional
    public Map<String, String> updateRoute(Routes route) {
        String updatedBy = (String) session.getAttribute("username");
        try {
            Routes routeDao = dao.findById(route.getId())
                    .orElseThrow(() -> new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Route Not Found"));
            routeDao.setRoute(route.getRoute());
            routeDao.setModified_by(updatedBy);
            routeDao.setModified_date(new Date());
            dao.save(routeDao);

            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Route Updated Successfully");
            return responseJson;
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Updating Route");
        }
    }

}
