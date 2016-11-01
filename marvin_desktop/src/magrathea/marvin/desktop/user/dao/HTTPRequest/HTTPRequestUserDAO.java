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
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
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
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
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
            // URL
            URL url = new URL("http://192.168.1.123/magrathea/marvin_server-master/usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user", "LOGIN_USER");               // User has rights?
            //params.put("param2", "getAllUser");
            //params.put("param3", "Prototip");             
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST

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

    /**
     * Construeix el missatge POST per enviar al servidor PHP
     *
     * @param params paràmetres del webservice
     * @return un byte[] amb els paràmetres
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
     * Processa el JSON i retorna un array JSON per construïr els objectes
     *
     * @param in Reader que retorna la connexió
     * @param node Nom del objectes JSON o null si es un array
     * @return un array d'objectes Json.
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
            user.setEmail(jsonobject.get("e-mail").getAsString());
            user.setAdministrator(jsonobject.get("administrator").getAsBoolean());
            users.add(user);
        }
        return users;
    }
}