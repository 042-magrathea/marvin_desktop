package magrathea.marvin.desktop.user.dao.HTTPRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.model.User;

/**
 * Concrete DAO implementation for User Class
 * @author Iván Cañizares Gómez
 */
public class HTTPRequestUserDAO extends HTTPRequestDAO implements UserDAO {

    private static final List<User> EMPTY = new ArrayList<>();

    @Override
    public long insertUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean updateUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<User> findUsersByProperty(UserSearchType searchType, Object user) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<User> findAll() {
        try {
            // URL
            URL url = new URL( Main.SERVER + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user", "LOGIN_USER");               // User has rights?
            //params.put("param2", "getAllUser");           // !DON'T NEED, ONLY TEST
            //params.put("param3", "Prototip");             
            byte[] postDataBytes = putParams(params);       // AUX. POST Method

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null); // "users" Only Json Objects

            // MAKE OBJECTS
            return makeUsersFromJson(jarray);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return EMPTY;
    }

    // TODO: Move this method to HTTPRequestDAO 
    // is generic for all query with POST parameters.
    /**
     * Binay UTF-8 encode for POST params stuff
     * @param params
     * @return 
     */
    private byte[] putParams(Map<String, Object> params) {
        byte[] postDataBytes = null;
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            postDataBytes = postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return postDataBytes;
    }

    /**
     * First parse on JsonData to JsonArray
     * @param in
     * @param node if the data contain a root object
     * @return JsonArray of Objects for the invoker class parser
     */
    private JsonArray getArrayFromJson(Reader in, String node) {
        JsonArray jarray = null;
        JsonElement jelement = new JsonParser().parse(in);
        if ( node != null){
            JsonObject jobject = jelement.getAsJsonObject();
            jarray = jobject.getAsJsonArray(node);
        } else {
            jarray = jelement.getAsJsonArray();
        }
        return jarray;
    }

    // TODO: Refactor the process of make attribute class 
    // Maybe a service class for process JSON arrays of class without inner class
    // that return a List for fill the List attribute.
    // LIKE List<Tournaments> JsonService.parseUsers(JsonArray ja)
    // this method then join all the stuff
    /**
     * Parser of Json for Users
     * Process Users
     * @param jarray
     * @return 
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
            user.setAdministrator((jsonobject.get("administrator").getAsInt() != 0));
            
            users.add(user);
        }
        return users;
    }
}