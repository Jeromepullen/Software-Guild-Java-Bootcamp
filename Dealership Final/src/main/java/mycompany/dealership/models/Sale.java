package mycompany.dealership.models;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class Sale {

    private int saleId;
    private int userId;
    private int vehicleId;
    private int purchaseTypeId;
    private int stateId;
    private String name;
    private String email;
    private String phone;
    private String addressOne;
    private String addressTwo;
    private String city;
    private String zipcode;
    private Timestamp timeOfSale;

    public Sale() {
    }

    public Timestamp getTimeOfSale() {
        return timeOfSale;
    }

    public void setTimeOfSale(Timestamp timeOfSale) {
        this.timeOfSale = timeOfSale;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(int purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.saleId;
        hash = 23 * hash + this.userId;
        hash = 23 * hash + this.vehicleId;
        hash = 23 * hash + this.purchaseTypeId;
        hash = 23 * hash + this.stateId;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.email);
        hash = 23 * hash + Objects.hashCode(this.phone);
        hash = 23 * hash + Objects.hashCode(this.addressOne);
        hash = 23 * hash + Objects.hashCode(this.addressTwo);
        hash = 23 * hash + Objects.hashCode(this.city);
        hash = 23 * hash + Objects.hashCode(this.zipcode);
        hash = 23 * hash + Objects.hashCode(this.timeOfSale);
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
        final Sale other = (Sale) obj;
        if (this.saleId != other.saleId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        if (this.vehicleId != other.vehicleId) {
            return false;
        }
        if (this.purchaseTypeId != other.purchaseTypeId) {
            return false;
        }
        if (this.stateId != other.stateId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.addressOne, other.addressOne)) {
            return false;
        }
        if (!Objects.equals(this.addressTwo, other.addressTwo)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.zipcode, other.zipcode)) {
            return false;
        }
        if (!Objects.equals(this.timeOfSale, other.timeOfSale)) {
            return false;
        }
        return true;
    }

}
