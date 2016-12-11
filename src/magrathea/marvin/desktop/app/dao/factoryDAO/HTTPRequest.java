package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.game.dao.GameDAO;
import magrathea.marvin.desktop.game.dao.HTTPRequestGameDAO;
import magrathea.marvin.desktop.host.dao.HTTPRequestHostDAO;
import magrathea.marvin.desktop.tournament.DAO.HTTPRequest.HTTPRequestTournamentDAO;
import magrathea.marvin.desktop.tournament.DAO.TournamentDAO;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.host.dao.HostDAO;
import magrathea.marvin.desktop.prize.dao.HTTPRequestPrizeDAO;
import magrathea.marvin.desktop.prize.dao.PrizeDAO;

/**
 *
 * @author boscalent
 */
public class HTTPRequest implements Idao{

    @Override
    public UserDAO getUserDao() {
        return new HTTPRequestUserDAO();
    }

    @Override
    public TournamentDAO getTournamentDao() {
        return new HTTPRequestTournamentDAO();
    }

    @Override
    public HostDAO getHostDao() {
        return new HTTPRequestHostDAO();
    }

    @Override
    public GameDAO getGameDAO() {
        return new HTTPRequestGameDAO();
    }

    @Override
    public PrizeDAO getPrizeDAO() {
        return new HTTPRequestPrizeDAO();
    }
    
}
