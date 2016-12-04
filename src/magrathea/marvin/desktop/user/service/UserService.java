/*
 * Capa de Servicio
 * ================
 * Sirve al controller para que la inyecte en la vista.
 * Solicita datos al modelo (una implementación DAO)
 */
package magrathea.marvin.desktop.user.service;

import java.util.List;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class UserService {

    private final UserDAO userDAO; // DI

    @Deprecated
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        try {
            this.userDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UserService() {
        userDAO = (UserDAO) DAOFactory.getInstance().getDAO().getUserDao();
        try {
            this.userDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addNewUser(String nickName, String password,
            String email, boolean administrator) {
        User user = new User();
        user.setNickname(nickName);
        user.setPassword(password);
        user.setEmail(email);
        user.setAdministrator(administrator);

        userDAO.insertUser(user);
    }

    public List<User> search(UserSearchType searchType, String value) {
        return userDAO.findUsersByProperty(searchType, value);
    }

    public long insertItem(User user) {
        return userDAO.insertUser(user);
    }

    public List<User> getAll() {
        return userDAO.findAll();
    }

    public void close() {
        try {
            userDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
