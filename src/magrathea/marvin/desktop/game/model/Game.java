package magrathea.marvin.desktop.game.model;

import java.net.URL;
import java.util.Objects;

/**
 *
 * @author boscalent
 */
public class Game {
    private long idGame;
    private String name;
    private String description;
    private String image;
    
    public Game() {
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
        final Game other = (Game) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

        
    // TO STRING
    @Override
    public String toString() {
        return "Game{" + "idGame=" + idGame 
                + ", name=" + name 
                + ", description=" + description 
                + ", image=" + image + '}';
    }


    // GETTER & SETTER
    public long getIdGame() {
        return idGame;
    }

    public void setIdGame(long idGame) {
        this.idGame = idGame;
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