package magrathea.marvin.desktop.tournament.model;

/**
 *
 * @author boscalent
 */
public class System {
    private long idSystem;
    private int name;
    private int nRounds;
    private int nPalyOffs;
    private int umpirePoints;
    private int goldPoints;
    private int silverPoints;
    private int bronzePoints;
    private int ironPoints;
    private int matchyPlayers;
    private int maxTeamPlayer;
    private int idGame;  

    public System() {
    }

    public long getIdSystem() {
        return idSystem;
    }

    public void setIdSystem(long idSystem) {
        this.idSystem = idSystem;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getnRounds() {
        return nRounds;
    }

    public void setnRounds(int nRounds) {
        this.nRounds = nRounds;
    }
    
    public int getnPalyOffs() {
        return nPalyOffs;
    }

    public void setnPalyOffs(int nPalyOffs) {
        this.nPalyOffs = nPalyOffs;
    }

    public int getUmpirePoints() {
        return umpirePoints;
    }

    public void setUmpirePoints(int umpirePoints) {
        this.umpirePoints = umpirePoints;
    }

    public int getGoldPoints() {
        return goldPoints;
    }

    public void setGoldPoints(int goldPoints) {
        this.goldPoints = goldPoints;
    }

    public int getSilverPoints() {
        return silverPoints;
    }

    public void setSilverPoints(int silverPoints) {
        this.silverPoints = silverPoints;
    }

    public int getBronzePoints() {
        return bronzePoints;
    }

    public void setBronzePoints(int bronzePoints) {
        this.bronzePoints = bronzePoints;
    }

    public int getIronPoints() {
        return ironPoints;
    }

    public void setIronPoints(int ironPoints) {
        this.ironPoints = ironPoints;
    }

    public int getMatchyPlayers() {
        return matchyPlayers;
    }

    public void setMatchyPlayers(int matchyPlayers) {
        this.matchyPlayers = matchyPlayers;
    }

    public int getMaxTeamPlayer() {
        return maxTeamPlayer;
    }

    public void setMaxTeamPlayer(int maxTeamPlayer) {
        this.maxTeamPlayer = maxTeamPlayer;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }
}