package magrathea.marvin.desktop.app.controller;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label errorMessage;

    private LoginService application;
    //private Main application;
    
    
    public void setApp(LoginService application){
        this.application = application;
    }
    //public void setApp(Main application){
    //    this.application = application;
    //}
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        userId.setPromptText("demo");
        password.setPromptText("demo");
        
    }
    
    
    public void processLogin(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            errorMessage.setText("Hello " + userId.getText());
        } else {
            //if (!application.userLogging(userId.getText(), password.getText())){
            //    errorMessage.setText("Username/Password is incorrect");
            //}
            application.gotoProfile();
        }
    }
}
