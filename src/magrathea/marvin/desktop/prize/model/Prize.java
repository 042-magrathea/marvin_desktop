package magrathea.marvin.desktop.prize.model;

import java.net.URL;
import java.sql.Date;
import java.util.Objects;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class Prize {
    
    private long idPrize;
    private String name;
    private String description;
    private String image;
    
    // FK - Tournament
    private long tournamentId;
    private int position;
    
    // FK - User
    private long userId;
    private boolean claimed_A; //?

    public Prize() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.name);
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
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    // TO STRING
    @Override
    public String toString() {
        return "Prize{" + "idPrize=" + idPrize
                + ", name=" + name
                + ", description=" + description
                + ", image=" + image + '}';
    }

    // GETTER & SETTER
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
}
