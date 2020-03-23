package mycompany.dealership.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class SearchCriteria {

    private int searchId;
    private String make;
    private String model;
    private int minYear;
    private int maxYear;
    private int minPrice;
    private int maxPrice;
    private int year;

    public SearchCriteria() {
    }

    public String getMake() {
        return make;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSearchId() {
        return searchId;
    }

    public void setSearchId(int searchId) {
        this.searchId = searchId;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.searchId;
        hash = 53 * hash + Objects.hashCode(this.make);
        hash = 53 * hash + Objects.hashCode(this.model);
        hash = 53 * hash + this.minYear;
        hash = 53 * hash + this.maxYear;
        hash = 53 * hash + this.minPrice;
        hash = 53 * hash + this.maxPrice;
        hash = 53 * hash + this.year;
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
        final SearchCriteria other = (SearchCriteria) obj;
        if (this.searchId != other.searchId) {
            return false;
        }
        if (this.minYear != other.minYear) {
            return false;
        }
        if (this.maxYear != other.maxYear) {
            return false;
        }
        if (this.minPrice != other.minPrice) {
            return false;
        }
        if (this.maxPrice != other.maxPrice) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.make, other.make)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        return true;
    }

}
