package mycompany.dealership.service;

import mycompany.dealership.service.VehicleService;
import mycompany.dealership.TestApplicationConfiguration;
import mycompany.dealership.models.BodyStyle;
import mycompany.dealership.models.Vehicle;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author jeromepullenjr
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class VehicleServiceTest {

    @Autowired
    VehicleService service;

    /*
    @Test
    public void testGetAllUsedVehicles() {
    }

    @Test
    public void testGetAllNewVehicles() {
    }

    @Test
    public void testGetAllFeaturedVehicles() {
    }

    @Test
    public void testGetAllModelsByBrand() {
    }

    @Test
    public void testMakeServiceCars() {
    }

    @Test
    public void testMakeServiceCarFromVehicle() {
    }

    @Test
    public void testMakeVehicleFromServiceCar() {
    }

    @Test
    public void testGetAllServiceCars() {
    }

    @Test
    public void testSearchForNewVehicles() {
    }

    @Test
    public void testSearchForUsedVehicles() {
    }

    @Test
    public void testSaveVehicle() {
    } */
}
