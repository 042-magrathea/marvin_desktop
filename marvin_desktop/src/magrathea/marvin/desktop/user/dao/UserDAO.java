/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.dao;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public interface UserDAO extends DAO {
    public long insertUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);
    
    public List<User> findUsersByProperty(UserSearchType searchType, Object user);
    public List<User> findAll();
}