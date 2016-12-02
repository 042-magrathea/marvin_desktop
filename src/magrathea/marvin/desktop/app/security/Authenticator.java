package magrathea.marvin.desktop.app.security;

import java.util.HashMap;
import java.util.Map;
import magrathea.marvin.desktop.user.model.User;

public class Authenticator {
    
    // CHANGE TO A METHOD IN USERS
    
    
    private static final Map<String, String> USERS = new HashMap<String, String>();
    static {
        USERS.put("demo", "demo");
    }
    
    private static final User[] LOGIN_USER;
    static {
        LOGIN_USER = new User[1];
        LOGIN_USER[0] = new User();
    }
    
    public static void putLoginUser( User user){
        LOGIN_USER[0] = user;
    }
    
    public static User getLoginUser(){
        return LOGIN_USER[0];
    }
    
    public static boolean validate(String user, String password){
        // TODO consulta Server
        String validUserPassword = USERS.get(user);
        return validUserPassword != null && validUserPassword.equals(password);
    }
}