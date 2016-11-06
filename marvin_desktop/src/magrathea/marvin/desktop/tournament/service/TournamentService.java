package magrathea.marvin.desktop.tournament.service;

import java.util.List;
import magrathea.marvin.desktop.tournament.DAO.TournamentDAO;
import magrathea.marvin.desktop.tournament.model.Tournament;

/**
 * Service layer
 * Prepare the different Request and others for Tournament
 * @author Iván Cañizares Gómez
 */
public class TournamentService {
    private final TournamentDAO tournamentDAO; // DI
    
    public TournamentService(TournamentDAO tournamentDAO ){
        this.tournamentDAO = tournamentDAO;
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