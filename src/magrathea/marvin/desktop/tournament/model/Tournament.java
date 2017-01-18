package magrathea.marvin.desktop.tournament.model;

import java.util.List;
import java.util.Objects;
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
    
    private TournamentStateType tournamentState;

    //private Host host;
    private String host;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicDes() {
        return publicDes;
    }

    public void setPublicDes(String publicDes) {
        this.publicDes = publicDes;
    }

    public String getPrivateDes() {
        return privateDes;
    }

    public void setPrivateDes(String privateDes) {
        this.privateDes = privateDes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TournamentStateType getTournamentState() {
        return tournamentState;
    }

    public void setTournamentState(TournamentStateType tournamentState) {
        this.tournamentState = tournamentState;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
 
    
}