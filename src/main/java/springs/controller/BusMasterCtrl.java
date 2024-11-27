package springs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springs.dto.BusMaster;
import springs.service.BusMasterService;

@Controller
public class BusMasterCtrl {

    @Autowired
    private BusMasterService service;

    @GetMapping("/bus")
    @Secured("ROLE_ADMIN")
    public String bus() {
        return "bus";
    }

    @GetMapping("/bus-pagination")
    @ResponseBody
    @Secured("ROLE_ADMIN")
    public Page<BusMaster> fetchBusMasters(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String search) {
        Page<BusMaster> bus = service.fetchBusMastersService(page,size,search);
        return bus;
    }

}
