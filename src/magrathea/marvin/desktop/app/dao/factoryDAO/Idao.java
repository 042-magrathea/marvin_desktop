/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.tournament.DAO.TournamentDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.game.dao.GameDAO;
import magrathea.marvin.desktop.host.dao.HostDAO;
import magrathea.marvin.desktop.prize.dao.PrizeDAO;

/**
 *
 * @author boscalent
 */
public interface Idao {
    UserDAO getUserDao();
    TournamentDAO getTournamentDao();
    HostDAO getHostDao();
    GameDAO getGameDAO();
    PrizeDAO getPrizeDAO();
}
