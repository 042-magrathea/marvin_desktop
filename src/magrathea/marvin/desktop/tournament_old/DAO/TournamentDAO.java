package magrathea.marvin.desktop.tournament_old.DAO;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.tournament_old.model.Tournament;

// TODO: These methods (Typical CRUD) can be a separate class?
// And implement params with generics (All classDAO do the same)
/**
 * Concrete DAO implementation for Tournament
 * @author Iván Cañizares Gómez
 */
public interface TournamentDAO extends DAO {
    public long insertTournament(Tournament tournament);
    public boolean updateTournament(Tournament tournament);
    public boolean deleteTournament(Tournament tournament);
    
    public List<Tournament> findTournamentByProperty(TournamentSearchType searchType, Object tournament);
    public List<Tournament> findAll();    
}