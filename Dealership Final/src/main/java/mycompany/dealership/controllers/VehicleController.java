package mycompany.dealership.controllers;

import mycompany.dealership.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author jeromepullenjr
 */
@Controller
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        //this.userService = userService;
        this.vehicleService = vehicleService;
    }

    @GetMapping("/inventory/new")
    public String getNewCars(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllNewVehicles());
        return "inventory-new";
    }

    @GetMapping("/inventory/used")
    public String getUsedCars(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllUsedVehicles());
        return "inventory-used";
    }

    @GetMapping("/inventory/{vehicleId}")
    public String getDetailedInfo(Model model, @PathVariable int vehicleId) {
        model.addAttribute("car", vehicleService.makeServiceCarFromVehicle(vehicleService.findByVehicleId(vehicleId)));
        return "inventory-details";
    }

}
