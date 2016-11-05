/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.tournament.DAO.HTTPRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
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
import magrathea.marvin.desktop.tournament.DAO.TournamentDAO;
import magrathea.marvin.desktop.tournament.DAO.TournamentSearchType;
import magrathea.marvin.desktop.tournament.model.Prize;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class HTTPRequestTournamentDAO extends HTTPRequestDAO implements TournamentDAO {

    private static final List<Tournament> EMPTY = new ArrayList<>();

    @Override
    public long insertTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tournament> findTournamentByProperty(TournamentSearchType searchType, Object tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tournament> findAll() {
        try {
            // URL
            URL url = new URL(Main.SERVER + "tournamentsQuery_fake.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("user", "");
            byte[] postDataBytes = putParams(params);

            // GET READER FROM CONN (Super)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = getArrayFromJson(in, null);

            // MAKE OBJECTS
            return makeTournamentsFromJson(jarray);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return EMPTY;
    }

    private List<Tournament> makeTournamentsFromJson(JsonArray jarray) {
        List<Tournament> tournaments = new ArrayList<>();
        Tournament tournament;
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();

            tournament = new Tournament();

            tournament.setId(jsonobject.get("idTOURNAMENT").getAsLong());
            tournament.setName("Tournament " + i );
            
            ///////////// PRIZES
            //JsonObject objectPrize = jsonobject.getAsJsonObject("prizes");
            JsonArray arrayPrize = jsonobject.getAsJsonArray("prizes");
            List<Prize> prizes = new ArrayList<>(arrayPrize.size());
            Prize prize = null;
            for ( int j = 0; j < arrayPrize.size(); j++){
                JsonObject jsonPrize = arrayPrize.get(j).getAsJsonObject();
                prize = new Prize();
                prize.setId(jsonPrize.get("idPRIZE").getAsLong());
                prize.setName(jsonPrize.get("name").getAsString());
                prizes.add(prize);
                //int order = jsonPrize.get("position").getAsInt();
                //prizes.add(order, prize);
            }
            tournament.setPrizes(prizes);
            
            ////////////// USERS
            //JsonObject objectPrize = jsonobject.getAsJsonObject("prizes");
            JsonArray arrayUsers = jsonobject.getAsJsonArray("users");
            List<User> users = new ArrayList<>(arrayUsers.size());
            User user = null;
            for ( int j = 0; j < arrayUsers.size(); j++){
                JsonObject jsonUser = arrayUsers.get(j).getAsJsonObject();
                user = new User();
                user.setId(jsonUser.get("idUSER").getAsLong());
                user.setNickname(jsonUser.get("name").getAsString());
                users.add(user);
            }
            tournament.setUsers(users);
            
            //////////
            
            tournaments.add(tournament);
        }
        return tournaments;
    }

    // TODO: MOVE THIS METHOD TO HTTPRequestDAO 
    // is generic IS GENERIC FOR ALL QUERYS with
    // POST parameters.
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
}
