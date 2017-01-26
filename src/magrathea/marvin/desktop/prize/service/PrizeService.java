package magrathea.marvin.desktop.prize.service;

import com.google.gson.JsonArray;
import java.util.List;
import magrathea.marvin.desktop.app.dao.factoryDAO.DAOFactory;
import magrathea.marvin.desktop.prize.dao.PrizeDAO;
import magrathea.marvin.desktop.prize.model.Prize;

/**
 *
 * @author boscalent
 */
public class PrizeService {

    private final PrizeDAO prizeDAO;

    public PrizeService() {
        this.prizeDAO = DAOFactory.getInstance().getDAO().getPrizeDAO();
        try {
            this.prizeDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Prize> getAll() {
        return prizeDAO.findAll();
    }
    
    public List<Prize> makePrizeFromJson(JsonArray jarray){
        return prizeDAO.makePrizeFromJson(jarray);
    }

    public void close() {
        try {
            prizeDAO.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
