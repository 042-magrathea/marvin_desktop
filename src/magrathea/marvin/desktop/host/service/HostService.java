package magrathea.marvin.desktop.host.service;

import java.util.List;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.host.dao.HostDAO;
import magrathea.marvin.desktop.host.model.Host;

/**
 *
 * @author boscalent
 */
public class HostService {

    private final HostDAO hostDAO;

    public HostService() {
        this.hostDAO = DAOFactory.getInstance().getDAO().getHostDao();
        try {
            this.hostDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Host> getAll() {
        return hostDAO.findAll();
    }

    public void close() {
        try {
            hostDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
