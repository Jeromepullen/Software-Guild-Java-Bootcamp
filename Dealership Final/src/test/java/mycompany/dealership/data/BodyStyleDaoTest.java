package mycompany.dealership.data;

import mycompany.dealership.data.VehicleDao;
import mycompany.dealership.data.BodyStyleDao;
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
public class BodyStyleDaoTest {

    @Autowired
    BodyStyleDao bDao;

    @Autowired
    VehicleDao vDao;

    public BodyStyleDaoTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Vehicle> cars = vDao.getAll();
        for (Vehicle car : cars) {
            vDao.deleteById(car.getVehicleId());
        }

        List<BodyStyle> styles = bDao.getAll();
        for (BodyStyle bodyStyle : styles) {
            bDao.deleteById(bodyStyle.getBodyStyleId());
        }
    }

    @Test
    public void testAddBodyStyle() {
        assertTrue(bDao.getAll().size() == 0);
        BodyStyle bodyStyle = new BodyStyle();
        bodyStyle.setName("Test Body Style");
        bodyStyle = bDao.add(bodyStyle);

        BodyStyle fromDao = bDao.findById(bodyStyle.getBodyStyleId());

        assertEquals(bodyStyle, fromDao);
    }

    @Test
    public void testGetAllBodyStyles() {
        assertTrue(bDao.getAll().size() == 0);
        BodyStyle bodyStyle = new BodyStyle();
        bodyStyle.setName("Test Body Style");
        bodyStyle = bDao.add(bodyStyle);

        BodyStyle secondBodyStyle = new BodyStyle();
        secondBodyStyle.setName("Second Test Body Style");
        secondBodyStyle = bDao.add(secondBodyStyle);
        List<BodyStyle> testStyles = bDao.getAll();

        assertEquals(2, testStyles.size());
        assertTrue(testStyles.contains(bodyStyle));
        assertTrue(testStyles.contains(secondBodyStyle));
    }

    @Test
    public void testUpdateBodyStyle() {
        assertTrue(bDao.getAll().size() == 0);
        BodyStyle bodyStyle = new BodyStyle();
        bodyStyle.setName("Test Name");
        bodyStyle = bDao.add(bodyStyle);

        BodyStyle fromDao = bDao.findById(bodyStyle.getBodyStyleId());

        assertEquals(bodyStyle, fromDao);

        bodyStyle.setName("Another Test Name");

        bDao.update(bodyStyle);

        assertNotEquals(bodyStyle, fromDao);

        fromDao = bDao.findById(bodyStyle.getBodyStyleId());

        assertEquals(bodyStyle, fromDao);
    }

    @Test
    public void testDeleteBodyStyle() {
        assertTrue(bDao.getAll().size() == 0);
        BodyStyle bodyStyle = new BodyStyle();
        bodyStyle.setName("Test Name");
        bodyStyle = bDao.add(bodyStyle);
        assertTrue(bDao.getAll().size() == 1);
        bDao.deleteById(bodyStyle.getBodyStyleId());
        assertTrue(bDao.getAll().size() == 0);
    }

}
