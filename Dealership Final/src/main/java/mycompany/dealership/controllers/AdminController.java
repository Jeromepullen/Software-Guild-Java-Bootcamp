package mycompany.dealership.controllers;

import mycompany.dealership.models.Role;
import mycompany.dealership.models.ServiceCar;
import mycompany.dealership.models.User;
import mycompany.dealership.models.Vehicle;
import mycompany.dealership.service.UserService;
import mycompany.dealership.service.VehicleService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jeromepullenjr
 */
@Controller
public class AdminController {

    @Autowired
    UserService users;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    PasswordEncoder encoder;

    //user and security management
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/admin")
    public String displayAdminPage(Model model) {
        model.addAttribute("users", users.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/error")
    public String displayErrorAdminPage(Model model) {
        model.addAttribute("users", users.getAllUsers());
        return "admin-error";
    }

    @GetMapping("admin/addUser")
    public String getAddUser(User user, Model model) {
        model.addAttribute("rolelist", users.getAllRoles());
        return "admin-add-user";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@Valid User user, int[] roleIds, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/admin/error";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        for (int currentId : roleIds) {
            roles.add(users.getRoleById(currentId));
        }
        user.setRoles(roles);
        user = users.addUser(user);

        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable int id) {
        users.deleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/deleteUser/{id}")
    public String confirmDelete(@PathVariable int id, Model model) {
        model.addAttribute("user", users.getUserById(id));
        return "admin-delete-user";
    }

    @GetMapping("/admin/editUser/{id}")
    public String editUserDisplay(Model model, @PathVariable Integer id, Integer error) {
        User user = users.getUserById(id);
        List roleList = users.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("rolelist", roleList);

        if (error != null) {
            if (error == 1) {
                model.addAttribute("error", "Passwords did not match, password was not updated.");
            }
        }

        return "editUser";
    }

    @PostMapping("/admin/editUser/{id}")
    public String editUserAction(String[] roleIdList, Boolean enabled, @PathVariable Integer id) {
        User user = users.getUserById(id);
        if (enabled != null) {
            user.setEnabled(enabled);
        } else {
            user.setEnabled(false);
        }

        Set<Role> roleList = new HashSet<>();
        for (String roleId : roleIdList) {
            Role role = users.getRoleById(Integer.parseInt(roleId));
            roleList.add(role);
        }
        user.setRoles(roleList);
        users.updateUser(user);

        return "redirect:/admin";
    }

    @PostMapping("admin/editPassword")
    public String editPassword(Integer id, String password, String confirmPassword) {
        User user = users.getUserById(id);

        if (password.equals(confirmPassword)) {
            user.setPassword(encoder.encode(password));
            users.updateUser(user);
            return "redirect:/admin";
        } else {
            return "redirect:/admin/editUser?id=" + id + "&error=1";
        }
    }

    //vehicle management
    @GetMapping("admin/addVehicle")
    public String getAddVehiclePage(Vehicle vehicle, Model model) {
        model.addAttribute("makes", vehicleService.getAllMakes());
        model.addAttribute("colors", vehicleService.getAllColors());
        model.addAttribute("styles", vehicleService.getAllBodyStyles());
        return "admin-vehicles-add";
    }

    @PostMapping("admin/addVehicle")
    public String addVehicle(Vehicle vehicle, BindingResult result, Model model) {
        if (vehicle.getMileage() <= 1000) {
            vehicle.setIsNew(true);
        } else {
            vehicle.setIsNew(false);
        }
        if (result.hasErrors()) {
            return "redirect:/admin/addVehicle";
        } else {
            vehicleService.saveVehicle(vehicle);
            return "redirect:/admin";
        }

    }

}
