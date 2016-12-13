package magrathea.marvin.desktop.user.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import magrathea.marvin.desktop.user.dao.UserSearchType;
import magrathea.marvin.desktop.user.service.UserService;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
@Deprecated
public class UserController_very_old {
    @FXML private ChoiceBox<UserSearchType> choiceBox;
    @FXML private ListView<User> listView;
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> UserId;
    @FXML private TableColumn<User, String> Nickname;
    @FXML private TableColumn<User, String> Password;
    @FXML private TableColumn<User, String> Email;
    @FXML private TableColumn<User, String> Administrator;
    
    private UserService service = null;
    
    public UserController_very_old( UserService service, Stage stage){
        this.service = service;
        stage.setOnCloseRequest(e -> service.close());
    }
    
    public void initialize(){
        choiceBox.getItems().setAll( UserSearchType.values() );     // ENUM values
        choiceBox.getSelectionModel().selectFirst();               // Only select one item
        
        listView.getItems().setAll(service.getAll());
        
        // COLUMNS
        UserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        Nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        Password.setCellValueFactory(new PropertyValueFactory<>("password"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Administrator.setCellValueFactory(new PropertyValueFactory<>("administrator"));
        
        // TABLE MODEL
        ObservableList<User> users = FXCollections.observableArrayList( service.getAll() );
        userTable.setEditable(true);
        userTable.setItems( users );
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // no bar    
    }
    
    // ACTIONS
    public void onSearch( ActionEvent event){
        String param = ((TextField) event.getSource()).getText();
        listView.getItems().setAll(service.search(choiceBox.getValue(), param));
    }
    
    public void onSendMail( ActionEvent event){
        if ( userTable.getSelectionModel().getSelectedItem() != null ){
            if (userTable.getSelectionModel().getSelectedItem().getEmail() != null ){
                String mail = userTable.getSelectionModel().getSelectedItem().getEmail();
                System.out.println("mailto:" + mail);
            } else {
                System.err.println("ERROR: USER with NULL mail");
            }
        } else {
            System.err.println("ERROR: NO select USER");
        }        
    }
}