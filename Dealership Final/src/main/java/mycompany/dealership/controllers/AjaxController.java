package mycompany.dealership.controllers;

import mycompany.dealership.models.CarModel;
import mycompany.dealership.models.Make;
import mycompany.dealership.models.SearchCriteria;
import mycompany.dealership.models.ServiceCar;
import mycompany.dealership.models.Vehicle;
import mycompany.dealership.service.VehicleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jeromepullenjr
 */
@RestController
public class AjaxController {

    private final VehicleService vehicleService;

    @Autowired
    public AjaxController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/inventory/new")
    public List<ServiceCar> searchNewCars(@RequestBody SearchCriteria search) {
        return vehicleService.searchForNewVehicles(search);
    }

    @PostMapping("/inventory/used")
    public List<ServiceCar> searchUsedCars(@RequestBody SearchCriteria search) {
        return vehicleService.searchForUsedVehicles(search);
    }

    @PutMapping("admin/addVehicle")
    public List<CarModel> getModelsForMake(@RequestBody Make make) {
        return vehicleService.getAllModelsByBrand(make.getName());
    }

}
