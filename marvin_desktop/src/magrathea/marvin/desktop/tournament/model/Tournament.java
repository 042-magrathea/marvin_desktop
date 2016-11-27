package magrathea.marvin.desktop.tournament.model;

import java.util.List;
import java.util.Objects;
import magrathea.marvin.desktop.user.model.User;

/**
 * Pojo class for DAO - Partial implementation for prototype
 * @author Iván Cañizares Gómez
 */
public class Tournament {
    private long id;
    private String name;
    
    private TournamentStateType state;

    private Host host;
    private List<Prize> prizes;
    private List<User> users;
    
    public Tournament(){
    }
    
    /**
     * TODO: Tournament STATE
     * @param state
     */
    public void changeTournamentStateTo(TournamentStateType state){};
    
    // Equals & HashCode

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.host);
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
        final Tournament other = (Tournament) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tournament{"
                + "" + "id=" + id 
                + ", name=" + name 
                + ", host=" + host 
                + ", prizes=" + prizes 
                + ", users=" + users 
                + ", state=" + state + '}';
    }
    
    // Getters & Setters
    public long getId() {return id;}
    public String getName() {return name;}
    public Host getHost() {return host;}
    public List<Prize> getPrizes() {return prizes;}
    public List<User> getUsers() {return users;}
    public TournamentStateType getState() {return state;}
    
    public void setId(long id) {this.id = id;}
    public void setName(String name) {this.name = name;    }
    public void setHost(Host host) {this.host = host;}
    public void setPrizes(List<Prize> prizes) {this.prizes = prizes;}
    public void setUsers(List<User> users) {this.users = users;}
    public void setState(TournamentStateType state) {this.state = state;}    
}