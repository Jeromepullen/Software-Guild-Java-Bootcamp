package mycompany.dealership.data;

import mycompany.dealership.data.MakeDao;
import mycompany.dealership.data.VehicleDao;
import mycompany.dealership.data.ModelDao;
import mycompany.dealership.TestApplicationConfiguration;
import mycompany.dealership.models.Make;
import mycompany.dealership.models.CarModel;
import mycompany.dealership.models.Vehicle;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class MakeDaoTest {

    @Autowired
    MakeDao makeDao;

    @Autowired
    VehicleDao vDao;

    @Autowired
    ModelDao modelDao;

    @BeforeEach
    public void setUp() {

        List<Vehicle> cars = vDao.getAll();
        for (Vehicle car : cars) {
            vDao.deleteById(car.getVehicleId());
        }

        List<Make> makes = makeDao.getAll();
        for (Make make : makes) {
            makeDao.deleteById(make.getMakeId());
        }
        List<CarModel> models = modelDao.getAll();
        for (CarModel model : models) {
            modelDao.deleteById(model.getModelId());
        }
    }

    @Test
    public void testAddMake() {
        assertTrue(makeDao.getAll().size() == 0);
        Make make = new Make();
        make.setName("Test Make");
        make = makeDao.add(make);

        Make fromDao = makeDao.findById(make.getMakeId());

        assertEquals(make, fromDao);
    }

    @Test
    public void testGetAllMakes() {
        assertTrue(makeDao.getAll().size() == 0);
        Make make = new Make();
        make.setName("Test Make");
        make = makeDao.add(make);

        Make secondMake = new Make();
        secondMake.setName("Second Test Make");
        secondMake = makeDao.add(secondMake);
        List<Make> testMakes = makeDao.getAll();

        assertEquals(2, testMakes.size());
        assertTrue(testMakes.contains(make));
        assertTrue(testMakes.contains(secondMake));
    }

    //no update or delete of make in specification
}
