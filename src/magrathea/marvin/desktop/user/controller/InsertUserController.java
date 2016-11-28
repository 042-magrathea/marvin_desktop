package magrathea.marvin.desktop.user.controller;

import custom.InsertUserForm;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.user.model.User;
import magrathea.marvin.desktop.user.service.UserService;
import javafx.event.EventHandler;

/**
 *
 * @author Tricoman
 */
public class InsertUserController {
        
    @FXML private InsertUserForm insertUserForm;
        
    private UserService service = null;

    public InsertUserController() {
        this.service = new UserService( Main.buildDAO("User") );

    }

    /*@Deprecated
    public InsertUserController(UserService service, Stage stage) {
        this.service = service;
    }*/

    public void initialize() {
        
        insertUserForm.getCreateUserButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                insertUser();
            }
        });
        
    }
    
    private void insertUser() {
        User user = new User();
        
        user.setNickname(insertUserForm.getNicknameField().getText());
        user.setName(insertUserForm.getNameField().getText());
        user.setPhone(insertUserForm.getPhoneField().getText());
        user.setEmail(insertUserForm.getEmailField().getText());
        user.setAds(insertUserForm.getAddsCheckbox().isSelected());
        user.setPrivateDes(insertUserForm.getPrivDescField().getText());
        user.setPublicDes(insertUserForm.getPubDescField().getText());
        user.setUserRole(insertUserForm.getRoleBox().getSelectionModel().getSelectedItem().toString());
        user.setLanguage(insertUserForm.getLanguageBox().getSelectionModel().getSelectedItem().toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        user.setDatePassword(dateFormat.format(date));
        user.setPassword(insertUserForm.getPasswordField().getText());
        user.setMemberSince(dateFormat.format(date));
        
        long insertionResult = service.insertItem(user);
            
        if (insertionResult == 0) {
            insertUserForm.getShowResult().setText("User creation has failed, please check al data and try again");
        } else {
            insertUserForm.getShowResult().setText("User has been created");
        }
               
    }
}
