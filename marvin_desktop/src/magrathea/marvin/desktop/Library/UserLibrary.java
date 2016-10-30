/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.library;

import java.util.List;
import magrathea.marvin.desktop.DAO.model.UserDAO;
import magrathea.marvin.desktop.DAO.model.UserSearchType;
import magrathea.marvin.desktop.model.User;

/**
 *
 * @author boscalent
 */
public class UserLibrary {
    
    private final UserDAO userDAO; // DI
    
    public UserLibrary( UserDAO userDAO ){
        this.userDAO = userDAO;
        try{
            this.userDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addNewUser(String nickName, String password, 
            String email, boolean administrator){
        User user = new User();
        user.setNickname(nickName);
        user.setPassword(password);
        user.setEmail(email);
        user.setAdministrator(administrator);
        
        userDAO.insertUser(user);
    }
    
    public List<User> search(UserSearchType searchType, String value){
        return userDAO.findUsersByProperty(searchType, value);
    }
    
    public void close(){
        try {
            userDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Check for user penalties
            
}