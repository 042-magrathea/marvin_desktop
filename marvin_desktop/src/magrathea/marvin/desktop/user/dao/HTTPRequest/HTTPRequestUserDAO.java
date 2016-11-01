/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.dao.HTTPRequest;

import java.util.List;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class HTTPRequestUserDAO extends HTTPRequestDAO implements UserDAO {

    @Override
    public long insertUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findUsersByProperty(UserSearchType searchType, Object user) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
}