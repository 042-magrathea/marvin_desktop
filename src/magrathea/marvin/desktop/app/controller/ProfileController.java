package magrathea.marvin.desktop.app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.app.service.LoginService;
import magrathea.marvin.desktop.user.model.User;

/**
 * Profile Controller.
 */
public class ProfileController extends AnchorPane implements Initializable {

    @FXML
    private TextField user;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextArea address;
    @FXML
    private CheckBox subscribed;
    @FXML
    private Hyperlink logout;
    @FXML 
    private Button save;
    
    @FXML 
    private Label success;
    
    /*
    private Main application;
    
    public void setApp(Main application){
        this.application = application;
        User loggedUser = application.getLoggedUser();
        //user.setText(loggedUser.getId());
        email.setText(loggedUser.getEmail());
        phone.setText(loggedUser.getPhone());
        //if (loggedUser.getAddress() != null) {
        //    address.setText(loggedUser.getAddress());
        //}
        //subscribed.setSelected(loggedUser.isSubscribed());
        //success.setOpacity(0);
    }
    */
    private LoginService application;
    
    
    public void setApp(LoginService application){
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            //animateMessage();
            //return;
        }
        //User loggedUser = application.getLoggedUser();
        //loggedUser.setEmail(email.getText());
        //loggedUser.setPhone(phone.getText());
        //loggedUser.setSubscribed(subscribed.isSelected());
        //loggedUser.setAddress(address.getText());
        //animateMessage();
    }
    
    public void resetProfile(ActionEvent event){
        if (application == null){
            return;
        }
        email.setText("");
        phone.setText("");
        subscribed.setSelected(false);
        address.setText("");
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
}
