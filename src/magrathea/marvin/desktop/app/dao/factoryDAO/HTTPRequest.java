package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.host.dao.HTTPRequestHostDAO;
import magrathea.marvin.desktop.host.dao.HostDAO;
import magrathea.marvin.desktop.tournament.DAO.HTTPRequest.HTTPRequestTournamentDAO;
import magrathea.marvin.desktop.tournament.DAO.TournamentDAO;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;

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
    
}
