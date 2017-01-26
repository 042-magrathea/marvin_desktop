package magrathea.marvin.desktop.tournament.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.Reader;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import magrathea.marvin.desktop.app.dao.HTTPRequestDAO.HTTPRequestDAO;
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.tournament.model.TournamentInfo;
import magrathea.marvin.desktop.tournament.model.TournamentStateType;

/**
 * Concrete DAO implementation for Tournament Class
 *
 * @author Iván Cañizares Gómez
 */
public class HTTPRequestTournamentDAO extends HTTPRequestDAO implements TournamentDAO {

    // dummy List for null returns
    private static final List<Tournament> EMPTY = new ArrayList<>();

    @Override
    public long insertTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean updateTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public boolean deleteTournament(Tournament tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public List<Tournament> findTournamentByProperty(TournamentSearchType searchType, Object tournament) {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public Map<String, Object> getTournamentInfo(long tournamentID) {
        try {
            // URL
            URL url = new URL(MarvinConfig.getInstance().getProperty("SERVER_ADDRESS")
                    + "tournamentsQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            // Exemple requestName=parseEntry&itemId=1
            params.put("requestName", "parseEntry");
            params.put("itemId", tournamentID);
            byte[] postDataBytes = helper.putParams(params);

            // GET READER FROM CONN (Super)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            JsonArray jarray = helper.getArrayFromJson(in, null);
            JsonObject jsonObject = jarray.get(0).getAsJsonObject();
            
            
            // PARSER
            Map<String, Object> map = new HashMap<>(); 
            map.put("prizes",jsonObject.get("prizes").getAsJsonArray());
            map.put("users",jsonObject.get("users").getAsJsonArray());
            map.put("system",jsonObject.get("system").getAsJsonObject());
            map.put("game", jsonObject.get("game").getAsJsonObject());
            map.put("host",jsonObject.get("host").getAsJsonObject());

            // MAKE OBJECTS
            return map;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

    }

    @Override
    public List<Tournament> findAll() {
        try {
            // URL
            URL url = new URL(MarvinConfig.getInstance().getProperty("SERVER_ADDRESS")
                    + "tournamentsQuery.php");

            // PARAMS POST
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("xx", "");
            byte[] postDataBytes = helper.putParams(params);

            // GET READER FROM CONN (Super)
            Reader in = super.connect(url, Proxy.NO_PROXY, postDataBytes);

            // PARSER
            JsonArray jarray = helper.getArrayFromJson(in, null);

            // MAKE OBJECTS
            return makeTournamentsFromJson(jarray);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return EMPTY;
    }

    // TODO: Refactor the process of make attribute class (Users & Prizes)
    // Maybe a service class for process JSON arrays of class without inner class
    // that return a List for fill the List attribute.
    // LIKE List<users> JsonService.parseUsers(JsonArray ja)
    // this method then join all the stuff
    /**
     * Parser of Json for Tournaments Process Tournament and their Prizes &
     * Users
     *
     * @param jarray
     * @return
     */
    private List<Tournament> makeTournamentsFromJson(JsonArray jarray) {
        List<Tournament> tournaments = new ArrayList<>();
        Tournament tournament;

        for (int i = 0; i < jarray.size(); i++) {
            JsonObject jsonobject = jarray.get(i).getAsJsonObject();

            tournament = new Tournament();
            tournament.setId(jsonobject.get("idTOURNAMENT").getAsLong());
            tournament.setName(jsonobject.get("name").getAsString());
            tournament.setDate(jsonobject.get("date").getAsString());
            tournament.setPublicDes(jsonobject.get("publicDes").getAsString());
            tournament.setPrivateDes(jsonobject.get("privateDes").getAsString());
            if (!jsonobject.get("SYSTEM_idSYSTEM").isJsonNull()) {
                tournament.setSystem(jsonobject.get("SYSTEM_idSYSTEM").getAsString());
            }
            tournament.setHost(jsonobject.get("TOURNAMENT_HOST_idTournamentHost").getAsString());
            if (!jsonobject.get("image").isJsonNull()) {
                tournament.setImage(jsonobject.get("image").getAsString());
            }
            tournament.setHost(jsonobject.get("TOURNAMENT_HOST_idTournamentHost").getAsString());
            tournament.setTournamentState(TournamentStateType.randomState());

            /*
            // PRIZES --> Move to Json Service
            JsonArray arrayPrize = jsonobject.getAsJsonArray("prizes");
            List<Prize> prizes = new ArrayList<>(arrayPrize.size());
            Prize prize = null;
            for ( int j = 0; j < arrayPrize.size(); j++){
                JsonObject jsonPrize = arrayPrize.get(j).getAsJsonObject();
                prize = new Prize();
                prize.setIdPrize(jsonPrize.get("idPRIZE").getAsLong());
                prize.setName(jsonPrize.get("name").getAsString());
                prizes.add(prize);
                //int order = jsonPrize.get("position").getAsInt();
                //prizes.add(order, prize);  // !NOT in prototype
            }
            tournament.setPrizes(prizes);
            
            // USERS --> Move to Json Service
            JsonArray arrayUsers = jsonobject.getAsJsonArray("users");
            List<User> users = new ArrayList<>(arrayUsers.size());
            User user = null;
            for ( int j = 0; j < arrayUsers.size(); j++){
                JsonObject jsonUser = arrayUsers.get(j).getAsJsonObject();
                user = new User();
                user.setId(jsonUser.get("idUSER").getAsLong());
                user.setNickname(jsonUser.get("publicName").getAsString());
                users.add(user);
            }
            tournament.setUsers(users);*/
            tournaments.add(tournament);
        }
        return tournaments;
    }
}
