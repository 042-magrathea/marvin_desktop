package magrathea.marvin.desktop.host.model;

import java.util.Objects;

/**
 *
 * @author boscalent
 */
public class Host {
    private long id;
    private String name;
    private float latitude;
    private float longitude;
    private String phone;
    private String address;
    private String eMail;

    public Host() {
    }

    // ToString
    @Override
    public String toString() {
        return "Host{" + "id=" + id 
                + ", name=" + name 
                + ", latitude=" + latitude 
                + ", longitude=" + longitude 
                + ", phone=" + phone 
                + ", address=" + address 
                + ", eMail=" + eMail + '}';
    }
     
    // Equals % Hashcode
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Float.floatToIntBits(this.latitude);
        hash = 83 * hash + Float.floatToIntBits(this.longitude);
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
        final Host other = (Host) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.latitude) != Float.floatToIntBits(other.latitude)) {
            return false;
        }
        if (Float.floatToIntBits(this.longitude) != Float.floatToIntBits(other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    // Setter
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    // Getter
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String geteMail() {
        return eMail;
    }
}