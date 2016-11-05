package magrathea.marvin.desktop.tournament.DAO;

import java.util.List;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.tournament.model.Tournament;

/**
 *
 * @author boscalent
 */
public interface TournamentDAO extends DAO {
    public long insertTournament(Tournament tournament);
    public boolean updateTournament(Tournament tournament);
    public boolean deleteTournament(Tournament tournament);
    
    public List<Tournament> findTournamentByProperty(TournamentSearchType searchType, Object tournament);
    public List<Tournament> findAll();    
}