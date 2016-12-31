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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 * Login Controller.
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField userId;
    @FXML
    PasswordField password, confirmPassword;
    @FXML
    Button login;
    @FXML
    Label errorMessage, confirmPwdLabel;
    @FXML
    ImageView loginImage;

    private ResourceBundle bundle;
    private LoginService loginService;
    
    // Login Service for callbacks
    public void setApp(LoginService application){
        this.loginService = application;
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        errorMessage.setText("");
        userId.setPromptText(bundle.getString("login_username_field_label"));
        password.setPromptText(bundle.getString("login_password_field_label"));
        loginImage.setImage(new Image( "magrathea/marvin/desktop/app/view/img/" +
                bundle.getString("login_login_image_src")));
    }
    
    // needs capture when pwd expires and needs to change
    public void processLogin(ActionEvent event) {
        if (loginService == null){
            // We are running in isolated FXML, possibly in Scene Builder. NO-OP.
            errorMessage.setText("Hello " + userId.getText());
        } else {
            if ( !loginService.userLogging( userId.getText(), password.getText() )){
                errorMessage.setText(
                        String.format(bundle.getString("login_message_error_login"), userId.getText()));
            } else {
                if ( password.getText().equals("marvin42")){ 
                    showInputPwd();
                } else {
                    loginService.gotoProfile();
                }
            }
        }
    }
    
    private void showInputPwd(){
        errorMessage.setText( String.format(bundle.getString("login_message_set_password"), userId.getText()));
        userId.setDisable(true);
        password.setText("");
        confirmPwdLabel.setVisible(true);
        confirmPassword.setVisible(true);
        login.setText(bundle.getString("login_save-pwd_button_text"));
    }
    
    // TODO: LOGIN CONTROLLER
    // Write Full Javadoc
    // Check if password & confirmPassword are equals like in User.
    // Write Tests for password & response of check logic
    // Write a states LOGIN | CHANGE
    // Write a cancel button in CHANGE state for return to LOGIN
    // Write a logic for pwd expiration in CHANGE State; review message
    // Change Pwd
}
