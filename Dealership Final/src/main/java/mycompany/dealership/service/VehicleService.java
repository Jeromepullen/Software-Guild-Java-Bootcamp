package mycompany.dealership.service;

import mycompany.dealership.data.BodyStyleDao;
import mycompany.dealership.data.ColorDao;
import mycompany.dealership.data.MakeDao;
import mycompany.dealership.data.ModelDao;
import mycompany.dealership.data.VehicleDao;
import mycompany.dealership.models.BodyStyle;
import mycompany.dealership.models.Color;
import mycompany.dealership.models.Make;
import mycompany.dealership.models.CarModel;
import mycompany.dealership.models.SearchCriteria;
import mycompany.dealership.models.ServiceCar;
import mycompany.dealership.models.Vehicle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.stereotype.Service;

/**
 * @author jeromepullenjr
 */
@Service
public class VehicleService {

    private final VehicleDao vehicleDao;
    private final ModelDao modelDao;
    private final ColorDao colorDao;
    private final MakeDao makeDao;
    private final BodyStyleDao bodyDao;

    public VehicleService(VehicleDao vehicleDao, ModelDao modelDao, ColorDao colorDao, MakeDao makeDao, BodyStyleDao bodyDao) {
        this.vehicleDao = vehicleDao;
        this.modelDao = modelDao;
        this.colorDao = colorDao;
        this.makeDao = makeDao;
        this.bodyDao = bodyDao;
    }

    private SearchCriteria lastSearch;

    public SearchCriteria getLastSearch() {
        return lastSearch;
    }

    public void setLastSearch(SearchCriteria lastSearch) {
        this.lastSearch = lastSearch;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.getAll();
    }

    public Vehicle findByVehicleId(int id) {
        if (vehicleDao.findById(id) == null) {
            return null;
        } else {
            return vehicleDao.findById(id);
        }
    }

    public List<BodyStyle> getAllBodyStyles() {
        return bodyDao.getAll();
    }

    public List<Vehicle> getAllUsedVehicles() {
        return vehicleDao.getAllUsedCars();
    }

    public List<Vehicle> getAllNewVehicles() {
        return vehicleDao.getAllNewCars();
    }

    public List<ServiceCar> getAllFeaturedVehicles() {
        return makeServiceCars(vehicleDao.getFeaturedVehicles());
    }

    public List<CarModel> getAllModels() {
        return modelDao.getAll();
    }

    public List<CarModel> getAllModelsByBrand(String brandName) {
        return modelDao.getAllByBrandName(brandName);
    }

    public List<Make> getAllMakes() {
        return makeDao.getAll();
    }

    public List<Color> getAllColors() {
        return colorDao.getAll();
    }

    public Make addMake(Make make) {

        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();

        Set<ConstraintViolation<Make>> violations = validator.validate(make);
        if (violations.size() > 0) {
            throw new RuntimeException("INVALID DATA.");
        }

        return makeDao.add(make);
    }

    public CarModel addModel(CarModel model) {

        Validator validator = Validation.buildDefaultValidatorFactory()
                .getValidator();

        Set<ConstraintViolation<CarModel>> violations = validator.validate(model);
        if (violations.size() > 0) {
            throw new RuntimeException("INVALID DATA.");
        }

        return modelDao.add(model);
    }

    //ServiceCar(Vehicle vehicle, Model model, Make make, Color interiorColor, Color exteriorColor, BodyStyle body)
    public List<ServiceCar> makeServiceCars(List<Vehicle> vehicles) {
        List<ServiceCar> output = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            output.add(new ServiceCar(vehicle,
                    modelDao.findById(vehicle.getModelId()),
                    makeDao.findByModelId(vehicle.getModelId()),
                    colorDao.findById(vehicle.getInteriorColorId()),
                    colorDao.findById(vehicle.getExteriorColorId()),
                    bodyDao.findById(vehicle.getBodyStyleId())));
        }
        return output;
    }

    public ServiceCar makeServiceCarFromVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        return makeServiceCars(vehicles).get(0);
    }

    public Vehicle makeVehicleFromServiceCar(ServiceCar car) {
        Vehicle v = car.getVehicle();
        BodyStyle style = car.getBody();
        Color exteriorColor = car.getExteriorColor();
        Color interiorColor = car.getInteriorColor();
        CarModel model = car.getModel();

        Vehicle vehicle = new Vehicle();
        vehicle.setBodyStyleId(style.getBodyStyleId());
        vehicle.setDescription(v.getDescription());
        vehicle.setExteriorColorId(exteriorColor.getColorId());
        vehicle.setInteriorColorId(interiorColor.getColorId());
        vehicle.setIsAutomatic(v.isIsAutomatic());
        vehicle.setIsFeatured(v.isIsFeatured());
        vehicle.setIsNew(v.isIsNew());
        vehicle.setMileage(v.getMileage());
        vehicle.setModelId(model.getModelId());
        vehicle.setMsrp(v.getMsrp());
        vehicle.setPrice(v.getPrice());
        vehicle.setVin(v.getVin());
        vehicle.setYear(v.getYear());
        vehicle = vehicleDao.add(vehicle);

        return vehicle;

    }

    public List<ServiceCar> getAllServiceCars() {
        return makeServiceCars(vehicleDao.getAll());
    }

    public List<ServiceCar> searchForNewVehicles(SearchCriteria search) {
        //compose new servicecar object for each service to return all of the good data
        List<Vehicle> allNewVehicles = vehicleDao.getVehicleSearchNew(search);
        List<Vehicle> output = new ArrayList<>();
        int size = allNewVehicles.size();
        if (search.getMaxPrice() == 0) {
            search.setMaxPrice(1000000);
        }

        if (search.getMinPrice() == 0) {
            search.setMinPrice(1);
        }

        if (search.getMinYear() == 0) {
            search.setMinYear(2000);
        }

        if (search.getMaxYear() == 0) {
            search.setMaxYear(2021);
        }

        BigDecimal max = new BigDecimal(search.getMaxPrice());
        BigDecimal min = new BigDecimal(search.getMinPrice());

        for (int i = 0; i < size; i++) {
            if ((allNewVehicles.get(i).getPrice().compareTo(max) <= 0)
                    && (allNewVehicles.get(i).getPrice().compareTo(min) >= 0)
                    && (allNewVehicles.get(i).getYear() >= search.getMinYear())
                    && (allNewVehicles.get(i).getYear() <= search.getMaxYear())) {
                output.add(allNewVehicles.get(i));
            }
        }
        return makeServiceCars(output);
    }

    public List<ServiceCar> searchForUsedVehicles(SearchCriteria search) {
        //compose new servicecar object for each service to return all of the good data
        List<Vehicle> allUsedVehicles = vehicleDao.getVehicleSearchUsed(search);
        List<Vehicle> output = new ArrayList<>();
        int size = allUsedVehicles.size();
        if (search.getMaxPrice() == 0) {
            search.setMaxPrice(1000000);
        }

        if (search.getMinPrice() == 0) {
            search.setMinPrice(1);
        }

        if (search.getMinYear() == 0) {
            search.setMinYear(2000);
        }

        if (search.getMaxYear() == 0) {
            search.setMaxYear(2021);
        }

        BigDecimal max = new BigDecimal(search.getMaxPrice());
        BigDecimal min = new BigDecimal(search.getMinPrice());

        for (int i = 0; i < size; i++) {
            if ((allUsedVehicles.get(i).getPrice().compareTo(max) <= 0)
                    && (allUsedVehicles.get(i).getPrice().compareTo(min) >= 0)
                    && (allUsedVehicles.get(i).getYear() >= search.getMinYear())
                    && (allUsedVehicles.get(i).getYear() <= search.getMaxYear())) {
                output.add(allUsedVehicles.get(i));
            }
        }
        return makeServiceCars(output);
    }

    public void saveVehicle(Vehicle v) {
        v = vehicleDao.add(v);
    }

}
