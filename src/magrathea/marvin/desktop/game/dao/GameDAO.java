/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.game.dao;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.game.model.Game;

/**
 *
 * @author boscalent
 */
public interface GameDAO extends DAO {
    public long insertHost(Game game);
    public boolean updateHost(Game game);
    public boolean deleteHost(Game game);
    
    public List<Game> findGameByProperties(GameSearchType searchType, Object host);
    public List<Game> findAll();
}
