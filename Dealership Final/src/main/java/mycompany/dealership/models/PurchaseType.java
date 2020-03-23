package mycompany.dealership.models;

import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class PurchaseType {

    private int purchaseTypeId;
    private String name;

    public int getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(int purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.purchaseTypeId;
        hash = 79 * hash + Objects.hashCode(this.name);
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
        final PurchaseType other = (PurchaseType) obj;
        if (this.purchaseTypeId != other.purchaseTypeId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
