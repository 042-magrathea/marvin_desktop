package magrathea.marvin.desktop.app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 * Profile Controller.
 */
public class ProfileController extends AnchorPane implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private WebView eula;
    
    @FXML
    private CheckBox subscribed;
    @FXML
    private Button logout, save;
    
    @FXML 
    private Label success;
    private LoginService application;
    private static final MarvinConfig PROPS = MarvinConfig.getInstance();
    
    public void setApp(LoginService application){
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayWeb();
    }
    
    public void processLogout(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }
        
        application.gotoLogin();
    }
    
    public void saveProfile(ActionEvent event) {
        // TODO: UPDATE USER
        animateMessage();
        application.gotoMain();
    }
    
    public void resetProfile(ActionEvent event){
        if (application == null){
            return;
        }
        subscribed.setSelected(false);
        success.setOpacity(0.0);
        
    }

    private void animateMessage() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
        ft.play();
    }
    
    public void gotoMain(){
        application.gotoMain();
    }
    
    
    public void displayWeb() {
        eula.setFontScale(0.66);
        WebEngine engine = eula.getEngine();
        final String html = "/magrathea/marvin/desktop/app/view/tos/EULA-" 
                + PROPS.getProperty("LANG") + ".html";                                        //arxiu HTML a mostrar
        
        URL urlFormHtml = getClass().getResource(html);
        engine.load(urlFormHtml.toExternalForm());
    }
}
