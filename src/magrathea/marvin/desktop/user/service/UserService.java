/*
 * Capa de Servicio
 * ================
 * Sirve al controller para que la inyecte en la vista.
 * Solicita datos al modelo (una implementación DAO)
 */
package magrathea.marvin.desktop.user.service;

import java.util.List;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserRole;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class UserService {
    
    public static final int NO_MATCH = 0;
    public static final int PUBLICNAME_FOUND = -1;
    public static final int NAME_FOUND = -2;
    public static final int PHONE_FOUND = -3;
    public static final int EMAIL_FOUND = -4;
    public static final int UNKNOW_ERROR = -5;

    private final UserDAO userDAO; // DI

    public UserService() {
        userDAO = DAOFactory.getInstance().getDAO().getUserDao();
        try {
            this.userDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public void addNewUser(String nickName, String password,
            String email, UserRole userRole) {
        User user = new User();
        user.setNickname(nickName);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserRole(userRole);

        userDAO.insertUser(user);
    }*/

    public List<User> search(UserSearchType searchType, String value) {
        return userDAO.findUsersByProperty(searchType, value);
    }

    public int insertItem(User user) {
        switch (this.userExists(user)) {
            case UserService.NO_MATCH:
                return userDAO.insertUser(user);
            case UserService.PUBLICNAME_FOUND:
                return UserService.PUBLICNAME_FOUND;
            case UserService.NAME_FOUND:
                return UserService.NAME_FOUND;
            case UserService.PHONE_FOUND:
                return UserService.PHONE_FOUND;
            case UserService.EMAIL_FOUND:
                return UserService.EMAIL_FOUND;
            default:
                return UserService.UNKNOW_ERROR;
        }

    }
    
    public boolean deleteItem(User user) {
        return userDAO.deleteUser(user) > 0;
    }
    
    public int modifyItem(User user) {
        
        return userDAO.updateUser(user);
        /*switch (this.userExists(user)) {
            case UserService.NO_MATCH:
                return userDAO.updateUser(user);
            case UserService.PUBLICNAME_FOUND:
                return UserService.PUBLICNAME_FOUND;
            case UserService.NAME_FOUND:
                return UserService.NAME_FOUND;
            case UserService.PHONE_FOUND:
                return UserService.PHONE_FOUND;
            case UserService.EMAIL_FOUND:
                return UserService.EMAIL_FOUND;
            default:
                return UserService.UNKNOW_ERROR;
        }*/
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
    
    public User validateAuthenticator(String login, String password){
        return userDAO.validateAuthenticator(login, password);
    }
    
    public int userExists(User user) {
        HTTPRequestUserDAO dao = (HTTPRequestUserDAO) userDAO;
        String publicName = user.getNickname();
        String name = user.getName();
        String eMail = user.getEmail();        
        if (dao.valueExists("publicName", publicName)) {
            return UserService.PUBLICNAME_FOUND;
        } else if (dao.valueExists("name", name)) {
            return UserService.NAME_FOUND;
        } else if (dao.valueExists("phone", name)) {
            return UserService.PHONE_FOUND;
        } else if (dao.valueExists("eMail", eMail)) {
            return UserService.EMAIL_FOUND;
        } else {
            return UserService.NO_MATCH;
        }
    }
}
