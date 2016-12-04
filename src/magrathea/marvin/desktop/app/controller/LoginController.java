package magrathea.marvin.desktop.app.controller;

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

    private LoginService loginService;
    //private Main application;
    
    // Login Service for callbacks
    public void setApp(LoginService application){
        this.loginService = application;
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        userId.setPromptText("login");
        password.setPromptText("password");
        
    }
    
    public void processLogin(ActionEvent event) {
        if (loginService == null){
            // We are running in isolated FXML, possibly in Scene Builder. NO-OP.
            errorMessage.setText("Hello " + userId.getText());
        } else {
            if ( !loginService.userLogging( userId.getText(), password.getText() )){
                errorMessage.setText("Username/Password is incorrect");
            } else {
                loginService.gotoProfile();
            }
            
        }
    }
}
