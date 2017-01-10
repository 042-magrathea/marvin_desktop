package magrathea.marvin.desktop.prize.model;


import java.util.Date;

/**
 *
 * @author boscalent
 */
public class Prize {
    private long idPrize;
    private String name;
    private String description;
    private String image;
    private long idUser;
    private long idTournament;
    private long idPrizeTemplate;
    private Date date;
    
    public Prize() {
    }

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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public long getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(long idTournament) {
        this.idTournament = idTournament;
    }

    public long getIdPrizeTemplate() {
        return idPrizeTemplate;
    }

    public void setIdPrizeTemplate(long idPrizeTemplate) {
        this.idPrizeTemplate = idPrizeTemplate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    

}