package mycompany.dealership.models;

import java.util.Objects;

/**
 * @author jeromepullenjr
 */
public class BodyStyle {

    private int bodyStyleId;
    private String name;

    public BodyStyle() {
    }

    public BodyStyle(String name) {
        this.name = name;
    }

    public int getBodyStyleId() {
        return bodyStyleId;
    }

    public void setBodyStyleId(int bodyStyleId) {
        this.bodyStyleId = bodyStyleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.bodyStyleId;
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final BodyStyle other = (BodyStyle) obj;
        if (this.bodyStyleId != other.bodyStyleId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
