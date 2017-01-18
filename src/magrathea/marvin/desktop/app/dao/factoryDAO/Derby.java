/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.game.dao.GameDAO;
import magrathea.marvin.desktop.host.dao.HostDAO;
import magrathea.marvin.desktop.prize.dao.PrizeDAO;
import magrathea.marvin.desktop.tournament.dao.TournamentDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;

/**
 *
 * @author boscalent
 */
public class Derby implements Idao {

    @Override
    public UserDAO getUserDao() {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public TournamentDAO getTournamentDao() {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public HostDAO getHostDao() {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public GameDAO getGameDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }

    @Override
    public PrizeDAO getPrizeDAO() {
        throw new UnsupportedOperationException("Not supported yet.");
        // TODO: Implement
    }
    
}
