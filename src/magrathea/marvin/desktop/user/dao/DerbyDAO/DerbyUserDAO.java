/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.dao.DerbyDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import magrathea.marvin.desktop.app.dao.DerbyDAO.DerbyDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.model.User;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 *
 * @author boscalent
 */
public class DerbyUserDAO extends DerbyDAO implements UserDAO {

    private static final List<User> EMPTY = new ArrayList<>();
    
    @Override
    public long insertUser(User user) {
        try {
            long id = dbAccess.insert(con,
                    "INSERT INTO \"User\"(nickname,password,email,administrator)"
                    + " VALUES (?,?,?,?)", new ScalarHandler<BigDecimal>(),
                    user.getNickname(), user.getPassword(), user.getEmail(), 
                    user.isAdministrator()
            ).longValue();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            dbAccess.update(con,
                    "UPDATE \"User\" SET nickname=?,password=?,email=?,administrator=? "
                            + "WHERE id=?",
                    user.getNickname(), user.getPassword(), user.getEmail(), 
                    user.isAdministrator(), user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        try {
            dbAccess.update(con, "DELETE FROM \"User\" WHERE id =?", user.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public List<User> findUsersByProperty(UserSearchType searchType, Object value) {
        String whereClause = "";
        String valueClause = "";
        
        switch (searchType){
            case ID:
                whereClause = "id = ?";
                valueClause = value.toString();
                break;
            case NICKNAME:
                whereClause = "nickname LIKE ?";
                valueClause = "%" + value.toString() + "%";
                break;
            case PASSWORD:
                whereClause = "password = ?";
                valueClause = value.toString();
                break;
            case EMAIL:
                whereClause = "email LIKE ?";
                valueClause = "%" + value.toString() + "%";
                break;
            case ADMINISTRATOR:
                whereClause = "administrator = ?";
                valueClause = value.toString();
                break;
            default:
                throw new AssertionError(searchType.name());
        }
        
        try {
            return dbAccess.query(con, "SELECT * FROM \"User\" WHERE " + whereClause, 
                    // proces a row from result to a bean
                    new BeanListHandler<User>(User.class), valueClause);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

    @Override
    public List<User> findAll() {
        try {
            return dbAccess.query(con, "SELECT * FROM \"User\"",  
                    new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

    @Override
    public User validateAuthenticator(String login, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }
}