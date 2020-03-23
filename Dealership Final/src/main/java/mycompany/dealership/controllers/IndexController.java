package mycompany.dealership.controllers;

import mycompany.dealership.models.Contact;
import mycompany.dealership.service.CustomerService;
import mycompany.dealership.service.VehicleService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author jeromepullenjr
 */
@Controller
public class IndexController {

    private final VehicleService vehicleService;
    private final CustomerService customerService;

    @Autowired
    public IndexController(VehicleService vehicleService, CustomerService customerService) {
        this.vehicleService = vehicleService;
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String getAllFeatured(Model model) {
        model.addAttribute("specials", customerService.getAllSpecials());
        model.addAttribute("featuredVehicles", vehicleService.getAllFeaturedVehicles());
        return "index";
    }

    @GetMapping("/home/specials")
    public String getSpecials(Model model) {
        model.addAttribute("featuredVehicles", vehicleService.getAllFeaturedVehicles());
        model.addAttribute("currentSpecials", customerService.getAllSpecials());
        return "specials";
    }

    @GetMapping("/home/contact")
    public String addContact(Contact contact, Model model) {
        return "contact";
    }

    @PostMapping("/home/contact")
    public String addContact(@Valid Contact contact, BindingResult result, Model model) {

        if (result.hasErrors()) {
            //maybe vin
            return "contact";
        }
        customerService.addContact(contact);

        return "redirect:/";
    }

}
