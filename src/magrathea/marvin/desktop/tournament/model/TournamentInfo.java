package magrathea.marvin.desktop.tournament.model;

import java.util.List;
import magrathea.marvin.desktop.game.model.Game;
import magrathea.marvin.desktop.host.model.Host;
import magrathea.marvin.desktop.prize.model.Prize;
import magrathea.marvin.desktop.user.model.User;

/**
 * All data for Tournament Info
 *
 * @author Iván Cañizares Gómez
 */
public class TournamentInfo {

    private List<User> users;
    private List<Prize> prizes;
    private Host host;
    private TournamentSystem system;
    private Game game;

    public TournamentInfo() {

    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public TournamentSystem getSystem() {
        return system;
    }

    public void setSystem(TournamentSystem system) {
        this.system = system;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    

}