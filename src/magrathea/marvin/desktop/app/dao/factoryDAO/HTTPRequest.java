package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.host.dao.HTTPRequestHostDAO;
import magrathea.marvin.desktop.tournament.DAO.HTTPRequest.HTTPRequestTournamentDAO;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;

/**
 *
 * @author boscalent
 */
public class HTTPRequest implements Idao{

    @Override
    public DAO getUserDao() {
        return new HTTPRequestUserDAO();
    }

    @Override
    public DAO getTournamentDao() {
        return new HTTPRequestTournamentDAO();
    }

    @Override
    public DAO getHostDao() {
        return new HTTPRequestHostDAO();
    }
    
}
