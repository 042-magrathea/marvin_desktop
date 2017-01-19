package magrathea.marvin.desktop.game.dao;

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
import magrathea.marvin.desktop.game.model.Game;

/**
 *
 * @author boscalent
 */
public class HTTPRequestGameDAO extends HTTPRequestDAO implements GameDAO {

    private static final List<Game> EMPTY = new ArrayList<>();

    @Override
    public List<Game> findAll() {
        try {

            URL url = new URL(MarvinConfig.getInstance().getProperty("SERVER_ADDRESS")
                    + "gamesQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("requestName", "allValues");               // User has rights?           
            byte[] postDataBytes = helper.putParams(params);       // Mètode aux. make POST

            // GET READER FROM CONN (SUPER)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null); // "users" Only Json Objects

            // MAKE OBJECTS
            return makeGameFromJson(jarray);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            con.disconnect();
        }
        return EMPTY;

        /*String fakeJson = "["
                + "{'idGame':'1','name':'Magic The Gathering','description':'Magic: The Gathering (MTG; also known as Magic) is a trading card game created by Richard Garfield. First published in 1993 by Wizards of the Coast, Magic was the first trading card game produced and it continues to thrive, with approximately twenty million players as of 2015.',"
                + "'image':'http://www.allcsgaming.com/wp-content/uploads/2014/03/MTGlogo.jpg'},"
                + "{'idGame':'2','name':'Pokemon TCG','description':'Juego de cartas coleccionables',"
                + "'image':'http://www.arkadian.vg/wp-content/uploads/2013/10/Pokemon-TCG.jpg'},"
                + "{'idGame':'3','name':'Aquelarre 2ª edición','description':'Juego de rol',"
                + "'image':'http://3.bp.blogspot.com/_M7qnJE4I_pA/TL2DVT7HQ1I/AAAAAAAAAQQ/V_mkFAqA4H4/s1600/aquelarre_latentacion_300.jpg'}"
                + "]";
        JsonParser jsonParser = new JsonParser();
        JsonArray fakeJsonArray = (JsonArray) jsonParser.parse(fakeJson);
        return makeGameFromJson(fakeJsonArray);*/
    }

    @Override
    public long insertHost(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean updateHost(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean deleteHost(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<Game> findGameByProperties(GameSearchType searchType, Object host) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    private List<Game> makeGameFromJson(JsonArray jarray) {
        List<Game> games = new ArrayList<>();
        Game game;
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();
            game = new Game();

            game.setIdGame(jsonobject.get("idGAME").getAsLong());
            game.setName(jsonobject.get("name").getAsString());
            game.setDescription(jsonobject.get("description").getAsString());
            if (!jsonobject.get("image").isJsonNull()) {game.setImage(jsonobject.get("image").getAsString()); }
            
            games.add(game);
        }
        return games;
    }
}
