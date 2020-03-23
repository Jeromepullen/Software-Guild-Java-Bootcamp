package mycompany.dealership.models;

import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class ServiceCar {

    private Vehicle vehicle;
    private CarModel model;
    private Make make;
    private Color interiorColor;
    private Color exteriorColor;
    private BodyStyle body;

    public ServiceCar() {
    }

    public ServiceCar(Vehicle vehicle, CarModel model, Make make, Color interiorColor, Color exteriorColor, BodyStyle body) {
        this.vehicle = vehicle;
        this.model = model;
        this.make = make;
        this.interiorColor = interiorColor;
        this.exteriorColor = exteriorColor;
        this.body = body;
    }

    public Color getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(Color interiorColor) {
        this.interiorColor = interiorColor;
    }

    public Color getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(Color exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public BodyStyle getBody() {
        return body;
    }

    public void setBody(BodyStyle body) {
        this.body = body;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.vehicle);
        hash = 47 * hash + Objects.hashCode(this.model);
        hash = 47 * hash + Objects.hashCode(this.make);
        hash = 47 * hash + Objects.hashCode(this.interiorColor);
        hash = 47 * hash + Objects.hashCode(this.exteriorColor);
        hash = 47 * hash + Objects.hashCode(this.body);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ServiceCar other = (ServiceCar) obj;
        if (!Objects.equals(this.vehicle, other.vehicle)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.make, other.make)) {
            return false;
        }
        if (!Objects.equals(this.interiorColor, other.interiorColor)) {
            return false;
        }
        if (!Objects.equals(this.exteriorColor, other.exteriorColor)) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        return true;
    }

}
