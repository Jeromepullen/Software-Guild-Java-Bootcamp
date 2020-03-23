package mycompany.dealership.data;

import mycompany.dealership.data.PurchaseTypeDao;
import mycompany.dealership.TestApplicationConfiguration;
import mycompany.dealership.models.BodyStyle;
import mycompany.dealership.models.PurchaseType;
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
public class PurchaseTypeDaoTest {

    @Autowired
    PurchaseTypeDao pDao;

    @BeforeEach
    public void setUp() {

        List<PurchaseType> purchases = pDao.getAll();
        for (PurchaseType purchase : purchases) {
            pDao.deleteById(purchase.getPurchaseTypeId());
        }
    }

    @Test
    public void testAddPurchaseType() {
        assertTrue(pDao.getAll().size() == 0);
        PurchaseType purchase = new PurchaseType();
        purchase.setName("Test Purchase Type");
        purchase = pDao.add(purchase);

        PurchaseType fromDao = pDao.findById(purchase.getPurchaseTypeId());

        assertEquals(purchase, fromDao);
    }

    @Test
    public void testGetAllBodyStyles() {
        assertTrue(pDao.getAll().size() == 0);
        PurchaseType purchase = new PurchaseType();
        purchase.setName("Test Purchase Type");
        purchase = pDao.add(purchase);

        PurchaseType secondPurchaseType = new PurchaseType();
        secondPurchaseType.setName("Second Test Purchase Type");
        secondPurchaseType = pDao.add(secondPurchaseType);
        List<PurchaseType> testTypes = pDao.getAll();

        assertEquals(2, testTypes.size());
        assertTrue(testTypes.contains(purchase));
        assertTrue(testTypes.contains(secondPurchaseType));
    }

    @Test
    public void testDeletePurchaseType() {
        assertTrue(pDao.getAll().size() == 0);
        PurchaseType purchaseType = new PurchaseType();
        purchaseType.setName("Test Name");
        purchaseType = pDao.add(purchaseType);
        assertTrue(pDao.getAll().size() == 1);
        pDao.deleteById(purchaseType.getPurchaseTypeId());
        assertTrue(pDao.getAll().size() == 0);
    }

}
