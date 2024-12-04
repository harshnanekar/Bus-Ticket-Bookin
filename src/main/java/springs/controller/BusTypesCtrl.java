package springs.controller;

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

import jakarta.servlet.http.HttpSession;
import springs.dto.BusTypes;
import springs.service.BusTypesService;

@Controller
public class BusTypesCtrl {

    @Autowired
    private HttpSession session;

    @Autowired
    private BusTypesService service;

    @GetMapping("/bus-types")
    @Secured("ROLE_ADMIN")
    public String renderBusTypes(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "bus-types";
    }

    @GetMapping("/bus-types-pagination")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Page<BusTypes> fetchBusTypesController(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String search) {
        Page<BusTypes> busTypes = service.fetchBusTypesService(page, size, search);
        return busTypes;
    }

    @GetMapping("/add-bus-type")
    @Secured("ROLE_ADMIN")
    public String addBusType(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "addBusType";
    }

    @PostMapping("/submit-bus-type")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> submitBusTypeController(@RequestBody BusTypes busTypes) {
        Map<String, String> json = service.submitBusTypeService(busTypes);
        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }

    @GetMapping("/delete-bus-type/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> deleteBusTypeController(@PathVariable int id) {
        Map<String, String> json = service.deleteBusTypeService(id);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @GetMapping("/fetch-bus-type/{id}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public BusTypes fetchBusTypeController(@PathVariable int id) {
        BusTypes busTypes = service.fetchBusTypeService(id);
        return busTypes;
    }

    @PostMapping("/update-bus-type")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> updateBusTypeController(@RequestBody BusTypes busTypes) {
        Map<String, String> json = service.updateBusTypeService(busTypes);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @GetMapping("/fetch-bus-types")
    @ResponseBody
    public List<BusTypes> fetchBusTypes() {
        List<BusTypes> busTypes = service.fetchAllBusTypes();
        return busTypes;
    }
}
