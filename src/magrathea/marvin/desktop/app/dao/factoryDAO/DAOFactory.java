package magrathea.marvin.desktop.app.dao.factoryDAO;

import magrathea.marvin.desktop.app.model.MarvinConfig;

/**
 *
 * @author boscalent
 */
public class DAOFactory {
   
    // implement here el Singlenton from properties
    private Idao concreteImplementationDAO = null;
    
    private DAOFactory(){
        
    }
    
    // Bill Pugh singleton pattern
    private static class LazyHolder {
        private static final DAOFactory INSTANCE = new DAOFactory();
        static {
            String dao = MarvinConfig.getInstance().getProperty("DAO");
            switch (dao){
                case "HTTPRequest": 
                    INSTANCE.concreteImplementationDAO = new HTTPRequest();
                    break;
                case "Derby":
                    INSTANCE.concreteImplementationDAO = new Derby();
                    break;
                default:
                    // null assigned
            }
        }
        
    }

    public static DAOFactory getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    
    public Idao getDAO(){
        return concreteImplementationDAO;
    }
}
