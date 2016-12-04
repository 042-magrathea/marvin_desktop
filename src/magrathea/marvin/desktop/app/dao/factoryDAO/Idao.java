/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.app.dao.DAO;

/**
 *
 * @author boscalent
 */
public interface Idao {
    DAO getUserDao();
    DAO getTournamentDao();
    DAO getHostDao();
}
