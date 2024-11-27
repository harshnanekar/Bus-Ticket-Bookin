package springs.service;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import springs.dto.BusTypes;
import springs.exceptions.DbExceptionHandler;
import springs.repository.BusTypesDao;

@Service
public class BusTypesService {

    @Autowired
    private BusTypesDao dao;

    @Autowired
    private HttpSession session;

    public Page<BusTypes> fetchBusTypesService(int page, int size, String search) {
        try {
            String searchValue = search.equals("null") ? null : search;
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            return dao.fetchBusTypesDao(pageable, searchValue);
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Types Not Found");
        }
    }

    @Transactional
    public Map<String, String> submitBusTypeService(BusTypes busType) {
        String createdBy = (String) session.getAttribute("username");
        BusTypes busTypeDao = new BusTypes();
        busTypeDao.setBus_type(busType.getBus_type());
        busTypeDao.setCreated_by(createdBy);
        busTypeDao.setCreated_date(new Date());
        busTypeDao.setActive(true);

        try {
            dao.save(busTypeDao);
            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Bus Type Added Successfully");
            return responseJson;
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Adding Bus Type");
        }
    }

    @Transactional
    public Map<String, String> deleteBusTypeService(int id) {
        String updatedBy = (String) session.getAttribute("username");
        try {
            BusTypes busTypes = dao.findById(id).orElseThrow(
                    () -> new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Type Not Found"));
            busTypes.setActive(false);
            busTypes.setModified_by(updatedBy);
            busTypes.setModified_date(new Date());
            dao.save(busTypes);

            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Bus Type Deleted Successfully");
            return responseJson;
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Deleting Bus Type");
        }
    }

    public BusTypes fetchBusTypeService(int id) {
        try {
            Optional<BusTypes> busTypes = dao.findById(id);
            if (busTypes.isPresent()) {
                return busTypes.get();
            } else {
                return new BusTypes();
            }

        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Type Not Found");
        }
    }

    @Transactional
    public Map<String, String> updateBusTypeService(BusTypes busType) {
        String updatedBy = (String) session.getAttribute("username");
        try {
            BusTypes busTypeDao = dao.findById(busType.getId())
                    .orElseThrow(() -> new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Bus Type Not Found"));
            busTypeDao.setBus_type(busType.getBus_type());
            busTypeDao.setModified_by(updatedBy);
            busTypeDao.setModified_date(new Date());
            dao.save(busTypeDao);

            Map<String, String> responseJson = new HashMap<>();
            responseJson.put("message", "Bus Type Updated Successfully");
            return responseJson;
        } catch (Exception e) {
            throw new DbExceptionHandler(HttpStatus.NOT_IMPLEMENTED, "Error In Updating Bus Type");
        }
    }

}
