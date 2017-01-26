/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.tournament.service;

import com.google.gson.JsonArray;
import java.util.List;
import java.util.Map;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.prize.service.PrizeService;
import magrathea.marvin.desktop.tournament.dao.TournamentDAO;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.tournament.model.TournamentInfo;
import magrathea.marvin.desktop.user.service.UserService;

/**
 *
 * @author boscalent
 */
public class TournamentService {

    private final TournamentDAO tournamentDAO; // DI
    private final UserService userService = new UserService();
    private final PrizeService prizeService = new PrizeService();

    @Deprecated
    public TournamentService(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
        try {
            this.tournamentDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public TournamentService() {
        this.tournamentDAO = DAOFactory.getInstance().getDAO().getTournamentDao();
        try {
            this.tournamentDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Tournament> getAll() {
        return tournamentDAO.findAll();
    }

    /**
     * Check Other controllers to parse
     *
     * @param tournamentID
     * @return All info plottt for a Tournament
     */
    public TournamentInfo getTournamentInfo(long tournamentID) {
        
        Map<String, Object> map = tournamentDAO.getTournamentInfo(tournamentID);
        TournamentInfo info = new TournamentInfo();
        info.setUsers(userService.makeUsersFromJson((JsonArray) map.get("users")));
        info.setPrizes(prizeService.makePrizeFromJson((JsonArray) map.get("prizes")));
        // info.setSystem(userService.makeUsersFromJson( (JsonArray)map.get("users")));
        //info.setGame(userService.makeUsersFromJson((JsonArray) map.get("users")));
        //info.setHost(userService.makeUsersFromJson((JsonArray) map.get("users")));
        return info;
    }

    public void close() {
        try {
            tournamentDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
