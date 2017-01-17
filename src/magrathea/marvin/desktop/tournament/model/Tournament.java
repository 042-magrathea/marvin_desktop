package magrathea.marvin.desktop.tournament.model;

import java.util.List;
import java.util.Objects;
import magrathea.marvin.desktop.game.model.Game;
import magrathea.marvin.desktop.host.model.Host;
import magrathea.marvin.desktop.prize.model.Prize;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class Tournament {
    private long id;
    
    private int maxPlayers;
    private int minPlayers;
    
    private String name;
    private String publicDes;
    private String privateDes;
    private String image;
    private String date;
    
    private TournamentStateType state;

    private Host host;
    private System system;
    private List<Prize> prizes;
    private List<User> users;
    
    
    // TODO: add new fields name, publicDes, privateDes, date
    
    public Tournament(){
    }
    
    /**
     * TODO: Tournament STATE
     * Logics for assign boolean states from DB
     * Can move to setter?
     */
    public void changeTournamentState(){};
    
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