package magrathea.marvin.desktop.game.service;

import java.util.List;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.game.dao.GameDAO;
import magrathea.marvin.desktop.game.model.Game;

/**
 *
 * @author boscalent
 */
public class GameService {

    private final GameDAO gameDAO;

    public GameService() {
        this.gameDAO = DAOFactory.getInstance().getDAO().getGameDAO();
        try {
            this.gameDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Game> getAll() {
        return gameDAO.findAll();
    }

    public void close() {
        try {
            gameDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
