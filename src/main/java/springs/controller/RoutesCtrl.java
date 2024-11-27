package springs.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import jakarta.servlet.http.HttpSession;
import springs.dto.Routes;
import springs.service.RoutesService;

@Controller
public class RoutesCtrl {

    private static final Logger logger = Logger.getLogger("routes"); 


    @Autowired
    private RoutesService service;

    @GetMapping("/routes")
    @Secured("ROLE_ADMIN")
    public String addRoutesPage(HttpSession session, Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "addRoutes";
    }

    @GetMapping("/routes-pagination")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Page<Routes> getRoutesDataController(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @RequestParam(defaultValue = "") String search) {
       logger.info("routes pagination controller");
       Page<Routes> routes = service.getRoutesDataService(page,size,search);
       return routes;
    }

    @GetMapping("/add-route")
    @Secured("ROLE_ADMIN")
    public String routeForm(HttpSession session, Model m) {
        List<Map<String, Object>> modules = (List<Map<String, Object>>) session.getAttribute("modules");
        m.addAttribute("modules", modules);
        return "route-form";
    }

    @PostMapping("/submit-route")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> submitRoute(@RequestBody Routes route) {
        return service.submitRoutesService(route);
    }

    @GetMapping("/delete-route/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> deleteRoute(@PathVariable int id) {
       Map<String,String> json = service.deleteRouteService(id);
       return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @GetMapping("/fetch-route/{id}")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Routes fetchRoute(@PathVariable int id) {
        Routes route = service.fetchRoute(id);
        return route;
    }

    @PostMapping("/update-route")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Object> updateRoute(@RequestBody Routes route) {
        Map<String,String> json = service.updateRoute(route);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }


}
