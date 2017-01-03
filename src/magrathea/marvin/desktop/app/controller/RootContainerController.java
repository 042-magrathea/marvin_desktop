package magrathea.marvin.desktop.app.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class RootContainerController extends BorderPane implements Initializable {

    private LoginService application;

    public void setApp(LoginService application) {
        this.application = application;
    }
    
    public LoginService getApp(){
        return application;
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }

    public void processLogout(ActionEvent event) {
        if (application == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }

        application.userLogout();
    }
}
