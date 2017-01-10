package magrathea.marvin.desktop.prize.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.prize.model.Prize;
import magrathea.marvin.desktop.prize.model.PrizeDiscount;
import magrathea.marvin.desktop.prize.model.PrizeMerchant;

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
        String fakeJsonTwo = "[\n"
                + "      {\n"
                + "	  'idPRIZE':'4',\n"
                + "	  'name':'Logro evento especial',\n"
                + "	  'description':'Logro concedido por participar en un evento especial',\n"
                + "	  'image':null,\n"
                + "	  'USER_idUSER':'2',\n"
                + "	  'TOURNAMENT_idTOURNAMENT':'1',\n"
                + "	  'TEMPLATE_idTEMPLATE':null,\n"
                + "	  'tournamentPosition':'2'},\n"
                + "	  {\n"
                + "	  'idPRIZE':'1',\n"
                + "	  'name':'MTG-Aether Hub',\n"
                + "	  'description':'Carta Promo Kaladesh - Firmada por el autor del arte',\n"
                + "	  'image':'https:\\/\\/es.magiccardmarket.eu\\/img\\/3648ebc583b',\n"
                + "	  'USER_idUSER':'1',\n"
                + "	  'TOURNAMENT_idTOURNAMENT':'3',\n"
                + "	  'TEMPLATE_idTEMPLATE':'1',\n"
                + "	  'claimed':'0',\n"
                + "	  'expirationDate':'2017-03-01'},\n"
                + "	  {\n"
                + "	  'idPRIZE':'2',\n"
                + "	  'name':'Pok\\u00e8mon-Eggecutor (Evolutions)',\n"
                + "	  'description':'Carta Promo Pok\\u00e8mon - Edici\\u00f3n limitada 2016',\n"
                + "	  'image':'https:\\/\\/es.pokemoncardmarket.eu\\/img\\/e81a8b6bd',\n"
                + "	  'USER_idUSER':'3',"
                + "         'TOURNAMENT_idTOURNAMENT':'2',\n"
                + "	  'TEMPLATE_idTEMPLATE':null,\n"
                + "	  'claimed':'0',\n"
                + "	  'expirationDate':'2016-12-31'},\n"
                + "	  {\n"
                + "	  'idPRIZE':'3',\n"
                + "	  'name':'Descuento 10%',\n"
                + "	  'description':'Un descuento que puedes usar para torneos y para tus compras',\n"
                + "	  'image':null,\n"
                + "	  'USER_idUSER':'3',\n"
                + "	  'TOURNAMENT_idTOURNAMENT':'1',\n"
                + "	  'TEMPLATE_idTEMPLATE':null,\n"
                + "	  'disc':'10',\n"
                + "	  'expirationDate':'2016-12-31'}\n"
                + "]";
        JsonParser jsonParser = new JsonParser();
        //JsonArray fakeJsonArray = (JsonArray) jsonParser.parse(fakeJson);
        JsonArray fakeJsonArray = (JsonArray) jsonParser.parse(fakeJsonTwo);
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
        Prize prize = null;
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();

            /*
                if (jsonobject.has("disc")) {
                    prize = new PrizeDiscount(jsonobject.get("disc").getAsInt());
                    prize.setDate(new Date(jsonobject.get("expirationDate").getAsString()));
                } else if (jsonobject.has("claimed")) {
                    prize = new PrizeMerchant(jsonobject.get("disc").getAsInt() != 0);
                    prize.setDate(new Date(jsonobject.get("expirationDate").getAsString()));
                } else {
                    prize = new Prize();
                    prize.setDate(null);
                }
             */
            prize = new Prize();

            if (jsonobject.has("name")) {
                if (jsonobject.get("name") != null) {
                    prize.setName(jsonobject.get("name").getAsString());
                }
            }

            prize.setName(jsonobject.get("name").getAsString());
            prize.setDescription(jsonobject.get("description").getAsString());
            //prize.setImage(jsonobject.get("image").getAsString());

            if (jsonobject.has("idPRIZE")) {
                if (jsonobject.get("idPRIZE") != null) {
                    prize.setIdPrize(jsonobject.get("idPRIZE").getAsLong());
                }
            }

            //prize.setIdPrize(jsonobject.get("idPRIZE").getAsLong());
            //prize.setIdPrizeTemplate(jsonobject.get("TEMPLATE_idTEMPLATE").getAsLong());
            //prize.setIdTournament(jsonobject.get("TOURNAMENT_idTOURNAMENT").getAsLong());
            //prize.setIdUser(jsonobject.get("USER_idUSER").getAsLong());

            prizes.add(prize);
        }
        return prizes;
    }
}
