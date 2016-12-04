/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.host.dao;

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
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.host.model.Host;

/**
 *
 * @author boscalent
 */
public class HTTPRequestHostDAO extends HTTPRequestDAO implements HostDAO {

    private static final List<Host> EMPTY = new ArrayList<>();

    /**
     * *****************************
     * TO AUX LIB
     */
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
        if (node != null) {
            JsonObject jobject = jelement.getAsJsonObject();
            jarray = jobject.getAsJsonArray(node);
        } else {
            jarray = jelement.getAsJsonArray();
        }
        return jarray;
    }

    /**
     * Construeix objectes tipus User
     *
     * @param jarray JsonArray amb objectes User en json
     * @return List<User> que es pot retornar a la capa de servei
     */
    private List<Host> makeHostFromJson(JsonArray jarray) {
        List<Host> hosts = new ArrayList<>();
        Host host;
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();
            host = new Host();

            host.setId(jsonobject.get("idTournamentHost").getAsLong());
            host.setName(jsonobject.get("name").getAsString());
            host.setLatitude(Float.parseFloat(jsonobject.get("latitude").getAsString()));
            host.setLongitude(Float.parseFloat(jsonobject.get("longitude").getAsString()));
            host.setPhone(jsonobject.get("phone").getAsString());
            host.setAddress(jsonobject.get("adress").getAsString());
            host.seteMail(jsonobject.get("eMail").getAsString());

            hosts.add(host);
        }
        return hosts;
    }

    @Override
    public long insertHost(Host host) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean updateHost(Host host) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean deleteHost(Host host) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<Host> findHostByProperties(HostSearchType searchType, Object host) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<Host> findAll() {
        try {
            // URL (TODO: fix URL server as Constant)
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "hostsQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "allValues");               // User has rights?           
            byte[] postDataBytes = putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null); // "users" Only Json Objects

            // MAKE OBJECTS
            return makeHostFromJson(jarray);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return EMPTY;
    }

}
