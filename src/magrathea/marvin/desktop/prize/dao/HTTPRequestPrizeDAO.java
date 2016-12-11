package magrathea.marvin.desktop.prize.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.prize.model.Prize;

/**
 *
 * @author boscalent
 */
public class HTTPRequestPrizeDAO extends HTTPRequestDAO implements PrizeDAO {

    private static final List<Prize> EMPTY = new ArrayList<>();


    @Override
    public List<Prize> findAll() {
        /* try {
            
            URL url = new URL( MarvinConfig.getInstance().getProperty("SERVER_ADDRESS") 
                    + "prizeQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "allValues");               // User has rights?           
            byte[] postDataBytes = helper.putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null); // "users" Only Json Objects

            // MAKE OBJECTS
            return makePrizeFromJson(jarray);
        

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return EMPTY;
        */
        String fakeJson = "["
                + "{'idPrize':'1','name':'Magic The Gathering','description':'Juego de cartas coleccionables',"
                + "'image':'http://www.allcsgaming.com/wp-content/uploads/2014/03/MTGlogo.jpg'},"
                + "{'idPrize':'2','name':'Pokemon TCG','description':'Juego de cartas coleccionables',"
                + "'image':'http://www.arkadian.vg/wp-content/uploads/2013/10/Pokemon-TCG.jpg'},"
                + "{'idPrize':'3','name':'Aquelarre 2ª edición','description':'Juego de rol',"
                + "'image':'http://3.bp.blogspot.com/_M7qnJE4I_pA/TL2DVT7HQ1I/AAAAAAAAAQQ/V_mkFAqA4H4/s1600/aquelarre_latentacion_300.jpg'}"
                + "]";
        JsonParser jsonParser = new JsonParser();
        JsonArray fakeJsonArray = (JsonArray) jsonParser.parse(fakeJson);
        return makePrizeFromJson(fakeJsonArray);
    }

    @Override
    public long insertHost(Prize prize) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean updateHost(Prize prize) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean deleteHost(Prize prize) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<Prize> findPrizeByProperties(PrizeSearchType searchType, Object host) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    private List<Prize> makePrizeFromJson(JsonArray jarray) {
        List<Prize> prizes = new ArrayList<>();
        Prize prize;
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();
            prize = new Prize();

            prize.setIdPrize(jsonobject.get("idPrize").getAsLong());
            prize.setName(jsonobject.get("name").getAsString());
            prize.setDescription(jsonobject.get("description").getAsString());
            prize.setImage(jsonobject.get("image").getAsString());
            
            prizes.add(prize);
        }
        return prizes;
    }
}