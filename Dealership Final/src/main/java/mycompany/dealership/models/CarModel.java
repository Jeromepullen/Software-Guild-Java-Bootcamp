package mycompany.dealership.models;

import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class CarModel {

    private int modelId;
    private int makeId;
    private String name;

    public CarModel() {
    }

    public CarModel(int makeId, String name) {
        this.makeId = makeId;
        this.name = name;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getMakeId() {
        return makeId;
    }

    public void setMakeId(int makeId) {
        this.makeId = makeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.modelId;
        hash = 47 * hash + this.makeId;
        hash = 47 * hash + Objects.hashCode(this.name);
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
        final CarModel other = (CarModel) obj;
        if (this.modelId != other.modelId) {
            return false;
        }
        if (this.makeId != other.makeId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
