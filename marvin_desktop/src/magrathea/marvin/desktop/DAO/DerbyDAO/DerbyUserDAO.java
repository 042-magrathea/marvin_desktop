/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.DAO.DerbyDAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import magrathea.marvin.desktop.DAO.model.UserDAO;
import magrathea.marvin.desktop.DAO.model.UserSearchType;
import magrathea.marvin.desktop.model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 *
 * @author boscalent
 */
public class DerbyUserDAO implements UserDAO {

    private Connection con;
    private QueryRunner dbAccess = new QueryRunner();   // Trhead Safe Query from Apache DBUtils
    private static final List<User> EMPTY = new ArrayList<>();

    @Override
    public void setup() throws Exception {
        // Create the DB
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/users;create=true");
        
        dbAccess.update(con, 
                "CREATE TABLE \"User\" ( id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY"
                + " (START WITH 1, INCREMENT BY 1), nickname VARCHAR(255), password VARCHAR(255), "
                + " email VARCHAR(255), administrator BOOLEAN );"
        );
                
                

    }

    @Override
    public void connect() throws Exception {
        // Don't create the DB
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/users");
    }

    @Override
    public void close() throws Exception {
        con.close();
        try {
            DriverManager.getConnection("jdbc:derby://localhost:1527/users;shutdown=true");
        } catch (Exception e) {
        }
    }

    @Override
    public long insertUser(User user) {
        try {
            long id = dbAccess.insert(con,
                    "INSERT INTO \"User\"(nickname,password,email,administrator)"
                    + " VALUES (?,?,?,?)", new ScalarHandler<BigDecimal>(),
                    user.getNickname(), user.getPassword(), user.getEmail(), user.isAdministrator()
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
            dbAccess.update(con, "UPDATE \"User\" SET nickname=?,password=?,email=?,administrator=? WHERE id=?",
                    user.getNickname(), user.getPassword(), user.getEmail(), user.isAdministrator(), user.getId());
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
                    new BeanListHandler<User>(User.class), valueClause);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

    @Override
    public List<User> findAll() {
        try {
            return dbAccess.query("SELECT * FROM \"User\"",  
                    new BeanListHandler<User>(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EMPTY;
    }

}
