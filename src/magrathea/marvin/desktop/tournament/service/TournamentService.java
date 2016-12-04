/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.tournament.service;

import java.util.List;
import javafx.fxml.FXMLLoader;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.tournament.DAO.TournamentDAO;
import magrathea.marvin.desktop.tournament.model.Tournament;

/**
 *
 * @author boscalent
 */
public class TournamentService {
    private final TournamentDAO tournamentDAO; // DI
    
    @Deprecated
    public TournamentService(TournamentDAO tournamentDAO ){
        this.tournamentDAO = tournamentDAO;
        try{
            this.tournamentDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public TournamentService(){
        this.tournamentDAO = (TournamentDAO) DAOFactory.getInstance().getDAO().getTournamentDao();
        try{
            this.tournamentDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Tournament> getAll(){
        return tournamentDAO.findAll();
    }
    
    public void close(){
        try {
            tournamentDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}