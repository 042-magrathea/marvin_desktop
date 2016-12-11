/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.prize.dao;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.prize.model.Prize;

/**
 *
 * @author boscalent
 */
public interface PrizeDAO extends DAO {
    public long insertHost(Prize prize);
    public boolean updateHost(Prize prize);
    public boolean deleteHost(Prize prize);
    
    public List<Prize> findPrizeByProperties(PrizeSearchType searchType, Object host);
    public List<Prize> findAll();
}
