package magrathea.marvin.desktop.tournament.model;

import java.util.Objects;

/**
 * Pojo class for DAO - Partial implementation for prototype
 * @author Iván Cañizares Gómez
 */
public class Prize {
    private long id;
    private String name;
    
    public Prize(){}
    
    // Equals & hashcode
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Prize other = (Prize) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    // toString
    
    @Override
    public String toString() {
        return "Prize{" 
                + "id=" + id 
                + ", name=" + name + '}';
    }

    // Getters & Setters
    
    public long getId() {return id;}
    public String getName() {return name;}

    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;}    
        
}