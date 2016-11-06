package magrathea.marvin.desktop.user.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.user.dao.UserRole;
import magrathea.marvin.desktop.user.dao.PreferedLanguage;
import magrathea.marvin.desktop.user.model.User;
import magrathea.marvin.desktop.user.service.UserService;

/**
 *
 * @author boscalent
 */
public class InsertUserController {
    
    @FXML private ChoiceBox<UserRole> roleBox;
    @FXML private ChoiceBox<PreferedLanguage> languageBox;
    @FXML private TextField nicknameField, nameField, phoneField, emailField, 
            passwordField, passConfirmationField, pubDescField, privDescField;
    @FXML private Label showResult; 
    @FXML private CheckBox addsCheckbox;
    
    
    
    private UserService service = null;

    public InsertUserController() {
        this.service = new UserService( Main.buildDAO("User") );
    }

    @Deprecated
    public InsertUserController(UserService service, Stage stage) {
        this.service = service;
    }

    public void initialize() {
        
        languageBox.getItems().setAll(PreferedLanguage.values());     // ENUM values
        languageBox.getSelectionModel().selectFirst();               // Only select one item
        roleBox.getItems().setAll(UserRole.values());     // ENUM values
        roleBox.getSelectionModel().selectFirst();               // Only select one item
        
        
    }

    // ACTIONS
    public void onCreateUser(ActionEvent event) {
        User user = new User();
        
        user.setNickname(nicknameField.getText());
        user.setName(nameField.getText());
        user.setPhone(phoneField.getText());
        user.setEmail(emailField.getText());
        user.setAds(addsCheckbox.isSelected());
        user.setPrivateDes(privDescField.getText());
        user.setPublicDes(pubDescField.getText());
        user.setUserRole(roleBox.getSelectionModel().getSelectedItem().toString());
        user.setLanguage(languageBox.getSelectionModel().getSelectedItem().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        user.setDatePassword(dateFormat.format(date));
        user.setPassword(passwordField.getText());
        user.setMemberSince(dateFormat.format(date));
        
        long insertionResult = service.insertItem(user);
            
        if (insertionResult == 0) {
            showResult.setText("User creation has failed, please check al data and try again");
        } else {
            showResult.setText("User has been created");
        }
               
    }
}
