package mycompany.dealership.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class Vehicle {

    private int vehicleId;
    private int modelId;
    private String vin;
    private boolean isNew;
    private int mileage;
    private int year;
    private int bodyStyleId;
    private boolean isAutomatic;
    private BigDecimal msrp;
    private BigDecimal price;
    private int exteriorColorId;
    private int interiorColorId;
    private String description;
    private String url;
    private boolean isFeatured;
    private boolean isPurchased;

    public Vehicle() {
    }

    public boolean isIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public boolean isIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBodyStyleId() {
        return bodyStyleId;
    }

    public void setBodyStyleId(int bodyStyleId) {
        this.bodyStyleId = bodyStyleId;
    }

    public boolean isIsAutomatic() {
        return isAutomatic;
    }

    public void setIsAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public BigDecimal getMsrp() {
        return msrp;
    }

    public void setMsrp(BigDecimal msrp) {
        this.msrp = msrp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getExteriorColorId() {
        return exteriorColorId;
    }

    public void setExteriorColorId(int exteriorColorId) {
        this.exteriorColorId = exteriorColorId;
    }

    public int getInteriorColorId() {
        return interiorColorId;
    }

    public void setInteriorColorId(int interiorColorId) {
        this.interiorColorId = interiorColorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.vehicleId;
        hash = 19 * hash + this.modelId;
        hash = 19 * hash + Objects.hashCode(this.vin);
        hash = 19 * hash + (this.isNew ? 1 : 0);
        hash = 19 * hash + this.mileage;
        hash = 19 * hash + this.year;
        hash = 19 * hash + this.bodyStyleId;
        hash = 19 * hash + (this.isAutomatic ? 1 : 0);
        hash = 19 * hash + Objects.hashCode(this.msrp);
        hash = 19 * hash + Objects.hashCode(this.price);
        hash = 19 * hash + this.exteriorColorId;
        hash = 19 * hash + this.interiorColorId;
        hash = 19 * hash + Objects.hashCode(this.description);
        hash = 19 * hash + Objects.hashCode(this.url);
        hash = 19 * hash + (this.isFeatured ? 1 : 0);
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
        final Vehicle other = (Vehicle) obj;
        if (this.vehicleId != other.vehicleId) {
            return false;
        }
        if (this.modelId != other.modelId) {
            return false;
        }
        if (this.isNew != other.isNew) {
            return false;
        }
        if (this.mileage != other.mileage) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (this.bodyStyleId != other.bodyStyleId) {
            return false;
        }
        if (this.isAutomatic != other.isAutomatic) {
            return false;
        }
        if (this.exteriorColorId != other.exteriorColorId) {
            return false;
        }
        if (this.interiorColorId != other.interiorColorId) {
            return false;
        }
        if (this.isFeatured != other.isFeatured) {
            return false;
        }
        if (!Objects.equals(this.vin, other.vin)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.msrp, other.msrp)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return true;
    }

}
