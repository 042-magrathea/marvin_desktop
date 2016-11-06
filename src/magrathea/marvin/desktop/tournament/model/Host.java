package magrathea.marvin.desktop.tournament.model;

import java.util.Objects;

/**
 *
 * @author boscalent
 */
public class Host {
    private long id;
    private String name;
    
    public Host(){}

    // equals & hashCode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.name);
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
        return true;
    }
    
    @Override
    public String toString() {
        return "Host{" 
                + "id=" + id 
                + ", name=" + name + '}';
    }
        
    // Getter & Setter
    public long getId() {return id;}
    public String getName() {return name;}
    
    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
}
