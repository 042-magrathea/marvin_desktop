package magrathea.marvin.desktop.app.service;

import java.util.HashMap;
import java.util.Map;
import magrathea.marvin.desktop.app.model.MarvinConfig;

/**
 * Class for all stuff of Style Inspired in Android R
 *
 * @author boscalent
 */
public class StyleService {

    // Active Style
    private Map<String, String> layoutDesign;

    public StyleService() {
        layoutDesign = new HashMap<>();
        chargeLayout();
    }

    ////////////////////// SINGLETON ///////////////////////////
    // Bill Pugh singleton pattern
    private static class LazyHolder {

        private static final StyleService INSTANCE = new StyleService();
    }

    public static StyleService getInstance() {
        return LazyHolder.INSTANCE;
    }
    ////////////////////// END SINGLETON ////////////////////////

    private void chargeLayout() {
        // load Layout based on style NOT FULLY IMPLEMENTED!!!
        String style = MarvinConfig.getInstance().getProperty("STYLE");
        // Then load property layout like style name
        // Here only one, hardcode
        
        layoutDesign.put("TOURNAMENT", "/magrathea/marvin/desktop/tournament/view/tournament.fxml");
        layoutDesign.put("HOME", "/magrathea/marvin/desktop/app/view/main_tab_home.fxml");
        layoutDesign.put("HOST", "/magrathea/marvin/desktop/host/view/host.fxml");
        layoutDesign.put("CONFIG", "/magrathea/marvin/desktop/app/view/main_tab_config.fxml");
        layoutDesign.put("GAME", "/magrathea/marvin/desktop/game/view/game.fxml");
        
        // Example in User only
        if ( style.equals("marvin")){
            layoutDesign.put("USER", "/magrathea/marvin/desktop/user/view/user.fxml");
        } else {
            layoutDesign.put("USER", "/magrathea/marvin/desktop/user/view/user.fxml");
        }
        
        // layoutDesign.put("Xx", "/magrathea/marvin/desktop/Yyy.fxml");
    }
    
    public Map getLayout(){
        return this.layoutDesign;
    }
}