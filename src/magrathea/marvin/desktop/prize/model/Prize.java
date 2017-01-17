package magrathea.marvin.desktop.prize.model;

/**
 *
 * @author boscalent
 */
public class Prize {

    private long idPrize;
    private String name;
    private String description;
    private String image;
    private String idUser;
    private String idTournament;
    private long idPrizeTemplate;
    private String date;
    //private String prizeState;

    // Internal stuff
    private String type;

    public Prize() {
        type = "Prize";
    }

    ////////
    // TO MUCH CASTING IN CONTROLLER
    ////////
    public String getTypePrice() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getPrizeState(){
        return getPrizeState(false);
    }
    
    public String getPrizeState(boolean verbose){
        return PrizeMessageFormatter.getInstance().getPrizeInfoMessage(this, verbose);
    }    
    ///////////////////////
    
    public long getIdPrize() {
        return idPrize;
    }

    public void setIdPrize(long idPrize) {
        this.idPrize = idPrize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(String idTournament) {
        this.idTournament = idTournament;
    }


    public long getIdPrizeTemplate() {
        return idPrizeTemplate;
    }

    public void setIdPrizeTemplate(long idPrizeTemplate) {
        this.idPrizeTemplate = idPrizeTemplate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
