/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import magrathea.marvin.desktop.DAO.model.UserSearchType;
import magrathea.marvin.desktop.library.UserLibrary;
import magrathea.marvin.desktop.model.User;

/**
 *
 * @author boscalent
 */
public class UserController {
    @FXML
    private ChoiceBox<UserSearchType> choiceBox;
    
    @FXML
    private ListView<User> listView;
    
    private UserLibrary model;
    
    public UserController( UserLibrary model, Stage stage){
        this.model = model;
        stage.setOnCloseRequest(e -> model.close());
    }
    
    public void initialize(){
        choiceBox.getItems().setAll( UserSearchType.values() );     // ENUM values
        choiceBox.getSelectionModel().selectFirst();                // Only select one item
        
    }
    
    public void onSearch( ActionEvent event){
        String param = ((TextField) event.getSource()).getText();
        listView.getItems().setAll(model.search(choiceBox.getValue(), param));
    }
}
