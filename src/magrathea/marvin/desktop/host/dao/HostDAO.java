/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.host.dao;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.host.model.Host;

/**
 *
 * @author boscalent
 */
public interface HostDAO extends DAO {
    public long insertHost(Host host);
    public boolean updateHost(Host host);
    public boolean deleteHost(Host host);
    
    public List<Host> findHostByProperties(HostSearchType searchType, Object host);
    public List<Host> findAll();
}
