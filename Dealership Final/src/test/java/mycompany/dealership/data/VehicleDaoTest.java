package mycompany.dealership.data;

import mycompany.dealership.data.ModelDao;
import mycompany.dealership.data.MakeDao;
import mycompany.dealership.data.BodyStyleDao;
import mycompany.dealership.data.VehicleDao;
import mycompany.dealership.data.ColorDao;
import mycompany.dealership.TestApplicationConfiguration;
import mycompany.dealership.models.BodyStyle;
import mycompany.dealership.models.Color;
import mycompany.dealership.models.Make;
import mycompany.dealership.models.CarModel;
import mycompany.dealership.models.Vehicle;
import java.math.BigDecimal;
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
public class VehicleDaoTest {

    @Autowired
    VehicleDao vDao;

    @Autowired
    ColorDao colorDao;

    @Autowired
    BodyStyleDao bodyDao;

    @Autowired
    ModelDao modelDao;

    @Autowired
    MakeDao makeDao;

    public VehicleDaoTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Vehicle> cars = vDao.getAll();
        for (Vehicle car : cars) {
            vDao.deleteById(car.getVehicleId());
        }

        List<CarModel> models = modelDao.getAll();
        for (CarModel model : models) {
            modelDao.deleteById(model.getModelId());
        }

        List<Make> makes = makeDao.getAll();
        for (Make make : makes) {
            makeDao.deleteById(make.getMakeId());
        }

        List<Color> colors = colorDao.getAll();
        for (Color color : colors) {
            colorDao.deleteById(color.getColorId());
        }

        List<BodyStyle> styles = bodyDao.getAll();
        for (BodyStyle style : styles) {
            bodyDao.deleteById(style.getBodyStyleId());
        }

    }

    @Test
    public void testAddBodyStyle() {
        Make make = makeDao.add(new Make("Test Make"));
        CarModel model = modelDao.add(new CarModel(make.getMakeId(), "Test Model"));
        Color color = colorDao.add(new Color("Blue"));
        BodyStyle style = bodyDao.add(new BodyStyle("Test Style"));
        assertTrue(makeDao.getAll().size() == 1);
        assertTrue(modelDao.getAll().size() == 1);
        assertTrue(colorDao.getAll().size() == 1);
        assertTrue(bodyDao.getAll().size() == 1);
        assertTrue(vDao.getAll().size() == 0);
        Vehicle car = new Vehicle();
        car.setBodyStyleId(style.getBodyStyleId());
        car.setDescription("Test Description");
        car.setExteriorColorId(color.getColorId());
        car.setInteriorColorId(color.getColorId());
        car.setIsAutomatic(true);
        car.setIsFeatured(false);
        car.setIsNew(false);
        car.setMileage(10000);
        car.setModelId(model.getModelId());
        car.setMsrp(BigDecimal.ZERO);
        car.setPrice(BigDecimal.ONE);
        car.setVin("1234");
        car.setYear(2005);
        car = vDao.add(car);

        Vehicle fromDao = vDao.findById(car.getVehicleId());

        assertEquals(car, fromDao);
    }

    @Test
    public void testUpdateVehicle() {
        Make make = makeDao.add(new Make("Test Make"));
        CarModel model = modelDao.add(new CarModel(make.getMakeId(), "Test Model"));
        Color color = colorDao.add(new Color("Blue"));
        BodyStyle style = bodyDao.add(new BodyStyle("Test Style"));
        assertTrue(makeDao.getAll().size() == 1);
        assertTrue(modelDao.getAll().size() == 1);
        assertTrue(colorDao.getAll().size() == 1);
        assertTrue(bodyDao.getAll().size() == 1);
        assertTrue(vDao.getAll().size() == 0);
        Vehicle car = new Vehicle();
        car.setBodyStyleId(style.getBodyStyleId());
        car.setDescription("Test Description");
        car.setExteriorColorId(color.getColorId());
        car.setInteriorColorId(color.getColorId());
        car.setIsAutomatic(true);
        car.setIsFeatured(false);
        car.setIsNew(false);
        car.setMileage(10000);
        car.setModelId(model.getModelId());
        car.setMsrp(BigDecimal.ZERO);
        car.setPrice(BigDecimal.ONE);
        car.setVin("1234");
        car.setYear(2005);
        car = vDao.add(car);

        Vehicle fromDao = vDao.findById(car.getVehicleId());

        assertEquals(car, fromDao);

        car.setYear(2008);

        vDao.update(car);

        assertNotEquals(car, fromDao);

        fromDao = vDao.findById(car.getVehicleId());

        assertEquals(car, fromDao);
    }

    @Test
    public void testDeleteById() {
        Make make = makeDao.add(new Make("Test Make"));
        CarModel model = modelDao.add(new CarModel(make.getMakeId(), "Test Model"));
        Color color = colorDao.add(new Color("Blue"));
        BodyStyle style = bodyDao.add(new BodyStyle("Test Style"));
        assertTrue(vDao.getAll().size() == 0);

        Vehicle car = new Vehicle();
        car.setBodyStyleId(style.getBodyStyleId());
        car.setDescription("Test Description");
        car.setExteriorColorId(color.getColorId());
        car.setInteriorColorId(color.getColorId());
        car.setIsAutomatic(true);
        car.setIsFeatured(false);
        car.setIsNew(false);
        car.setMileage(10000);
        car.setModelId(model.getModelId());
        car.setMsrp(BigDecimal.ZERO);
        car.setPrice(BigDecimal.ONE);
        car.setVin("1234");
        car.setYear(2005);
        car = vDao.add(car);

        vDao.deleteById(car.getVehicleId());

        assertTrue(vDao.getAll().size() == 0);
    }

}
