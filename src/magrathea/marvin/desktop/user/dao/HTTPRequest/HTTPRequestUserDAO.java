package magrathea.marvin.desktop.user.dao.HTTPRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.user.dao.PreferedLanguage;
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
    private final String[] FIELDS = {
        "publicName", "name", "phone", "eMail", "ads", "privateDes", "publicDes", 
        "userRole", "language", "datePassword", "password", "memberSince"
        };

    @Override
    public int insertUser(User user) {
        

        //values array creation
        String[] values = buildValuesArray(user);
        
        Gson gson = new Gson();
        
        String fieldsJson = gson.toJson(FIELDS);
        String valuesJson = gson.toJson(values);

        
        try {
            // URL (TODO: fix URL server as Constant)
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "insertItem");               // User has rights?
            
            
            params.put("values", valuesJson);
            params.put("fields", fieldsJson);
                           
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null); // "users" Only Json Objects

            JsonObject jObject = jarray.get(0).getAsJsonObject();
            // MAKE OBJECTS
            return jObject.get("insertionId").getAsInt();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return 0;
    }

    @Override
    public int updateUser(User user) {
        //values array creation
        String[] values = buildValuesArray(user);
        
        Gson gson = new Gson();
        
        //get POST parameters Strings converted to JSON
        String fieldsJson = gson.toJson(FIELDS);
        String valuesJson = gson.toJson(values);

        
        try {
            // URL (TODO: fix URL server as Constant)
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "modifyItem");               // User has rights?
            
            params.put("itemId", user.getId());
            params.put("values", valuesJson);
            params.put("fields", fieldsJson);
                           
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null);

            JsonObject jObject = jarray.get(0).getAsJsonObject();
            // MAKE OBJECTS
            int serverResponse = jObject.get("modifiedRowsNum").getAsInt();
            
            
            return serverResponse;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
            
        } 
        return 0;
    }

    @Override
    public int deleteUser(User user) {
        boolean result = false;
       
        try {
            // URL (TODO: fix URL server as Constant)
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "deleteItem");               // User has rights?
            params.put("itemId", user.getId());
                           
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null);

            JsonObject jObject = jarray.get(0).getAsJsonObject();
            
            int serverResponse = jObject.get("deletedRowsNum").getAsInt();


            return serverResponse;

        } catch (Exception ex) {
            
            ex.printStackTrace();
        } finally {
            
            con.disconnect();

        }
        return 0;
        
    }

    @Override
    public List<User> findUsersByProperty(UserSearchType searchType, Object user) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> findAll() {
        try {
            // URL (TODO: fix URL server as Constant)
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user", "LOGIN_USER");               // User has rights?           
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
    

    private String[] buildValuesArray(User user) {
        String[] values = new String[12];
        values[0] = user.getNickname();
        values[1] = user.getName();
        values[2] = user.getPhone();
        values[3] = user.getEmail();
        values[4] = null;
        values[5] = user.getPrivateDes();
        values[6] = user.getPublicDes();
        values[7] = user.getUserRole().toString();
        values[8] = user.getLanguage().toString();
        values[9] = user.getDatePassword();
        values[10] = user.getPassword();
        values[11] = user.getMemberSince();
        
        return values;
    }
    
    
    /**
     * Checks if exists any entry in USER table of DB with an specific value for 
     * an specific field
     * 
     * @param field field to check
     * @param value value of the field
     * @return boolean
     */
    public boolean valueExists(String field, String value) {
        boolean result = false;
        
        try {
            // URL (TODO: fix URL server as Constant)
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "usersQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();

            params.put("requestName", "valueExists");
            params.put("field", field);
            params.put("value", value);
            
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null); // "users" Only Json Objects

            JsonObject jObject = jarray.get(0).getAsJsonObject();
            
            result = jObject.get("result").getAsBoolean();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            
            con.disconnect();
            return result;
        }
        
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
            System.out.println(postData.toString());
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
            user.setName(jsonobject.get("name").getAsString());
            user.setPhone(jsonobject.get("phone").getAsString());
            user.setEmail(jsonobject.get("eMail").getAsString());
            if (jsonobject.has("administrator") 
                    && !jsonobject.get("administrator").isJsonNull() 
                    && jsonobject.get("administrator").getAsBoolean()) {
                user.setAdministrator();
            } else if (jsonobject.has("editor") 
                    && !jsonobject.get("editor").isJsonNull() 
                    && jsonobject.get("editor").getAsBoolean()) {
                user.setEditor();
            } else {
                user.setRegularUser();
            }
            if (jsonobject.has("language") && !jsonobject.get("language").isJsonNull()) {
                String language = jsonobject.get("language").getAsString();
                if (language.equalsIgnoreCase(PreferedLanguage.catalan.toString())) {
                    user.setLanguage(PreferedLanguage.catalan);                        
                } else if (language.equalsIgnoreCase(PreferedLanguage.english.toString())) {
                    user.setLanguage(PreferedLanguage.english);
                } else if (language.equalsIgnoreCase(PreferedLanguage.spanish.toString())) {
                    user.setLanguage(PreferedLanguage.spanish);
                }
            } 
            if (jsonobject.has("publicDes")&& !jsonobject.get("publicDes").isJsonNull()) {
                user.setPublicDes(jsonobject.get("publicDes").getAsString());
            }
            if (jsonobject.has("privateDes")&& !jsonobject.get("privateDes").isJsonNull()) {
                user.setPrivateDes(jsonobject.get("privateDes").getAsString());
            }
            
            
            
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
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST
            
            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null); // "users" Only Json Objects
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