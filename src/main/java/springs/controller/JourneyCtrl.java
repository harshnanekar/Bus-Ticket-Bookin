package springs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import springs.dto.BusMaster;
import springs.dto.Journey;
import springs.dto.Routes;
import springs.service.BusMasterService;
import springs.service.JourneyService;
import springs.service.RoutesService;

@Controller
public class JourneyCtrl {

    @Autowired
    private HttpSession session;

    @Autowired
    private JourneyService service;

    @Autowired
    private BusMasterService busMasterService;
    
    @Autowired
    private RoutesService routesService;

    @GetMapping("/journey")
    @Secured("ROLE_ADMIN")
    public String bus(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "journey";
    }

    @GetMapping("/journey-pagination")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Page<Journey> journeyDetails(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String search) {
        Page<Journey> journey = service.fetchJourneyDetails(page, size, search);
        return journey;
    }

    @GetMapping("/add-journey")
    @Secured("ROLE_ADMIN")
    public String addJourney(Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        List<BusMaster> bus = busMasterService.fetchAllBuses();
        List<Routes> routes = routesService.fetchAllRoutes();
        m.addAttribute("modules", modules);
        m.addAttribute("bus", bus);
        m.addAttribute("routes", routes);
        return "add-journey";
    }
}
