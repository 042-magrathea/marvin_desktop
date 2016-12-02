/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.model;

import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;

/**
 *
 * @author boscalent
 */
public class UserJson {
    User user = new User();
    HTTPRequestUserDAO dao = new HTTPRequestUserDAO();
    
    public UserJson() {
        user.setEmail("boscalent@gmail.com");
        user.setAds(true);
        user.setId(15);
        
        dao.insertUser(user);
    }

    public static void main(String[] args){
        UserJson main = new UserJson();
    }
            
}
