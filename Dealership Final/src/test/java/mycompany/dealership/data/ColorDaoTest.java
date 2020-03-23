package mycompany.dealership.data;

import mycompany.dealership.data.VehicleDao;
import mycompany.dealership.data.ColorDao;
import mycompany.dealership.TestApplicationConfiguration;
import mycompany.dealership.models.Color;
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
public class ColorDaoTest {

    @Autowired
    ColorDao cDao;

    @Autowired
    VehicleDao vDao;

    public ColorDaoTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Vehicle> cars = vDao.getAll();
        for (Vehicle car : cars) {
            vDao.deleteById(car.getVehicleId());
        }

        List<Color> colors = cDao.getAll();
        for (Color color : colors) {
            cDao.deleteById(color.getColorId());
        }
    }

    @Test
    public void testAddColor() {
        assertTrue(cDao.getAll().size() == 0);
        Color color = new Color();
        color.setName("Test Color");
        color = cDao.add(color);

        Color fromDao = cDao.findById(color.getColorId());

        assertEquals(color, fromDao);
    }

    @Test
    public void testGetAllColors() {
        assertTrue(cDao.getAll().size() == 0);
        Color color = new Color();
        color.setName("Test Color");
        color = cDao.add(color);

        Color secondColor = new Color();
        secondColor.setName("Second Test Color");
        secondColor = cDao.add(secondColor);
        List<Color> testColors = cDao.getAll();

        assertEquals(2, testColors.size());
        assertTrue(testColors.contains(color));
        assertTrue(testColors.contains(secondColor));
    }

    @Test
    public void testDeleteColor() {
        assertTrue(cDao.getAll().size() == 0);
        Color color = new Color();
        color.setName("Test Name");
        color = cDao.add(color);
        assertTrue(cDao.getAll().size() == 1);
        cDao.deleteById(color.getColorId());
        assertTrue(cDao.getAll().size() == 0);
    }

}
