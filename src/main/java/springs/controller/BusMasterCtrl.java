package springs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import springs.dto.BusMaster;
import springs.dto.BusTypes;
import springs.repository.BusTypesDao;
import springs.service.BusMasterService;
import springs.service.BusTypesService;

@Controller
public class BusMasterCtrl {

    @Autowired
    private BusMasterService service;

    @Autowired
    private BusTypesService busTypesService;

    @Autowired
    private HttpSession session;

    @Autowired
    private BusTypesDao dao;

    @GetMapping("/bus")
    @Secured("ROLE_ADMIN")
    public String bus(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "bus";
    }

    @GetMapping("/bus-pagination")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Page<BusMaster> fetchBusMasters(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String search) {
        Page<BusMaster> bus = service.fetchBusMastersService(page, size, search);
        return bus;
    }

    @GetMapping("/add-bus")
    @Secured("ROLE_ADMIN")
    public String busForm(Model model) {
        List<BusTypes> busTypes = busTypesService.fetchAllBusTypes();
        model.addAttribute("bus_types", busTypes);
        return "add-bus";
    }

    @PostMapping("/submit-bus")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> submitBus(@RequestBody BusMaster bus) {
        Map<String, String> responseJson = service.submitBusService(bus);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    @GetMapping("/fetch-bus/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseBody
    public BusMaster fetchBus(@PathVariable int id) {
        BusMaster bus = service.fetchBusService(id);
        return bus;
    }

}
