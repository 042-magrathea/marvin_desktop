/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.host.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.Reader;
import java.net.Proxy;
import java.net.URL;
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
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "hostsQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "allValues");               // User has rights?           
            byte[] postDataBytes = helper.putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null); // "users" Only Json Objects

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