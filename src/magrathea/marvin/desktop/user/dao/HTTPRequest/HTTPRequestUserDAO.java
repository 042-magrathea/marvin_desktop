package magrathea.marvin.desktop.user.dao.HTTPRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserRole;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class HTTPRequestUserDAO extends HTTPRequestDAO implements UserDAO {

    private static final List<User> EMPTY = new ArrayList<>();

    @Override
    public long insertUser(User user) {
        
        String[] fields = {
        "publicName", "name", "phone", "eMail", "ads", "privateDes", "publicDes", 
        "userRole", "language", "datePassword", "password", "memberSince"
        };
        //values array creation
        String[] values = new String[12];
        values[0] = user.getNickname();
        values[1] = user.getName();
        values[2] = user.getPhone();
        values[3] = user.getEmail();
        values[4] = String.valueOf(user.getAdds());
        values[5] = user.getPrivateDes();
        values[6] = user.getPublicDes();
        values[7] = user.getUserRole();
        values[8] = user.getLanguage();
        values[9] = user.getDatePassword();
        values[10] = user.getPassword();
        values[11] = user.getMemberSince();
        
        Gson gson = new Gson();
        
        String fieldsJson = gson.toJson(fields);
        String valuesJson = gson.toJson(values);

        System.out.println(fieldsJson);
        System.out.println(valuesJson);
        
        try {
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "insertItem");               // User has rights?
            
            
            params.put("values", valuesJson);
            params.put("fields", fieldsJson);
                           
            byte[] postDataBytes = helper.putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null); // "users" Only Json Objects

            JsonObject jObject = jarray.get(0).getAsJsonObject();
            // MAKE OBJECTS
            System.out.println(jObject.get("insertionId").getAsInt());
            return jObject.get("insertionId").getAsLong();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return 0;
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
        try {
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user", "LOGIN_USER");               // User has rights?           
            byte[] postDataBytes = helper.putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null); // "users" Only Json Objects

            // MAKE OBJECTS
            return makeUsersFromJson(jarray);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return EMPTY;
    }
    
    public boolean userExist(String userPublicName, String userEmail) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Construeix objectes tipus User
     * @param jarray  JsonArray amb objectes User en json
     * @return List<User> que es pot retornar a la capa de servei
     */
    private List<User> makeUsersFromJson(JsonArray jarray) {
        List<User> users = new ArrayList<>();
        User user;
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();
            user = new User();
            user.setId(jsonobject.get("idUser").getAsLong());
            user.setNickname(jsonobject.get("publicName").getAsString());
            user.setPassword(jsonobject.get("password").getAsString());
            user.setEmail(jsonobject.get("eMail").getAsString());
            // getAsBoolean falla, fico el if
            // user.setAdministrator(jsonobject.get("administrator").getAsBoolean());
            user.setAdministrator(jsonobject.get("administrator").getAsBoolean());
            
            users.add(user);
        }
        return users;
    }

    @Override
    public User validateAuthenticator(String login, String password) {
        User loginUser = null;
        try { 
            
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS")
                    + "usersQuery.php");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "userLogin");            
            params.put("userPublicName", login);                           
            params.put("userPassword", password);           // TODO: plain text, secure IT!!!!!               
            byte[] postDataBytes = helper.putParams(params);       // Mètode aux. make POST
            
            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null); // "users" Only Json Objects
            JsonObject jsonobject = jarray.get(0).getAsJsonObject();
            int role = jsonobject.get("loginResult").getAsInt();
            
            //int role = 3;
            if (role == 0){
                // !!! Server says wrong User 
            } else if( role > 0 && role < UserRole.values().length ){
                loginUser = new User();         // TODO: FILL with Query of user
                loginUser.setNickname(login);
                loginUser.setUserRole( UserRole.getById(role));
            } else {
                // Unexpected result !!!
            }
           
        } catch (MalformedURLException ex ) {
            //
        } catch (Exception ex) {
            //
        }
        return loginUser;
    }
}