package magrathea.marvin.desktop.user.dao;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.user.model.User;

// TODO: These methods (Typical CRUD) can be a separate class?
// And implement params with generics (All classDAO do the same)
/**
 * Concrete DAO implementation for User
 * @author Iván Cañizares Gómez
 */
public interface UserDAO extends DAO {
    public long insertUser(User user);
    public boolean updateUser(User user);
    public boolean deleteUser(User user);
    
    public List<User> findUsersByProperty(UserSearchType searchType, Object user);
    public List<User> findAll();
}