package magrathea.marvin.desktop.user.dao.HTTPRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
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
            URL url = new URL("http://192.168.1.123/magrathea/userRequest.php");
            Map<String, Object> params = new LinkedHashMap<>();

            params.put("param", "Magrathera Marvin v.01 - GetAllUsers");

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length",
                    String.valueOf(postDataBytes.length));
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            JsonElement jelement = new JsonParser().parse(in);
            JsonObject jobject = jelement.getAsJsonObject();
            JsonArray jarray = jobject.getAsJsonArray("users");

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

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return EMPTY;
    }
}
