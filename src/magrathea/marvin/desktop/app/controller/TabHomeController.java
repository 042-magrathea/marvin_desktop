package magrathea.marvin.desktop.app.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class TabHomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    LoginService application;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setApp(LoginService application){
          this.application = application;
    }
    
}
