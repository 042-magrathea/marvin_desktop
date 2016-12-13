package magrathea.marvin.desktop.user.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import magrathea.marvin.desktop.user.dao.PreferedLanguage;
import magrathea.marvin.desktop.user.dao.UserRole;
import magrathea.marvin.desktop.user.model.User;
import magrathea.marvin.desktop.user.service.UserService;
import magrathea.marvin.desktop.user.view.fields.DataTextField;
import magrathea.marvin.desktop.user.view.fields.EmailTextField;
import magrathea.marvin.desktop.user.view.fields.PasswordTextField;

/**
 *
 * @author tricoman
 */
public class UserController {

    public static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:"
            + "\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c"
            + "\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b"
            + "\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"
            + "\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]"
            + "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?"
            + "[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e"
            + "-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e"
            + "-\\x7f])+)\\])";
    public static final String PHONE_REGEX = "\\d{9}";
    public static final String NAME_REGEX = "^[\\p{L}\\p{M}' \\.\\-]+$";

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userId;
    @FXML
    private TableColumn<User, String> nickname;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> email;
    @FXML
    private TableColumn<User, String> phone;
    @FXML
    private TableColumn<User, String> userRole;
    @FXML
    private Button resetPassButton;
    @FXML
    private ChoiceBox<UserRole> roleBox;
    @FXML
    private ChoiceBox<PreferedLanguage> languageBox;
    @FXML
    private TextField nicknameField;
    @FXML
    private DataTextField nameField, phoneField;
    @FXML
    private EmailTextField emailField;
    @FXML
    private PasswordTextField passwordField, passConfirmationField;
    @FXML
    private TextArea pubDescField, privDescField;
    @FXML
    private Button createUserButton;

    private UserService service = null;
    
    
    @FXML GridPane inputFields;
    @FXML GridPane extraFields;
    private List<TextInputControl> userTextFields;
    
    private enum STATE {
        READ, NEW, EDIT, DELETE
    }
    STATE state;

    

    public UserController() {
        this.service = new UserService();
    }

    @Deprecated
    public UserController(UserService service, Stage stage) {
        this.service = service;
    }

    public void initialize() {
        userTextFields = new ArrayList<>();
        
        
        // Add all instances of TextInputControl in a List for iterate later
        for (Node node : inputFields.getChildren()) {
            if (node instanceof TextField) {
                userTextFields.add((TextField) node);
            } else if (node instanceof DataTextField) {
                userTextFields.add((DataTextField) node);
            } else if (node instanceof EmailTextField) {
                userTextFields.add((EmailTextField) node);
            } else if (node instanceof PasswordTextField) {
                userTextFields.add((PasswordTextField) node);
            }
        }
        // Add all instances of TextInputControl in a List for iterate later
        for (Node node : extraFields.getChildren()) {
            if (node instanceof TextArea) {
                userTextFields.add((TextArea) node);
            }
        }
        
        
        //---------------------------------------------------------------------
        //USER TABLE SPECS
        
        
        //COLUMNS
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));

        //TABLE MODEL
        userTable.setEditable(false);
        refreshUserTable();
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // no bar
        
        //LISTENER
        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (userTable.getSelectionModel().getSelectedItem() != null) {
                    User person = userTable.getSelectionModel().getSelectedItem();
                    fromUserToForm(person); 
                    state = STATE.READ;
                    setInterface();
                }
            }
        });     
        
        
        
        //---------------------------------------------------------------------
        
        // FORM FIELDS
        nameField.setInputRegex(NAME_REGEX);
        phoneField.setInputRegex(PHONE_REGEX);        
//        resetPassButton.setText("   set\ndefault");
        languageBox.getItems().setAll(PreferedLanguage.values());     // ENUM values
        languageBox.getSelectionModel().selectFirst();               // Only select one item
        roleBox.getItems().setAll(UserRole.values());     // ENUM values
        roleBox.getSelectionModel().selectFirst();               // Only select one item

        
        //LISTENER
        passConfirmationField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    inputDataValidation();
                }
            }

        });
        state = STATE.READ;
        setInterface();
        
    }

    //ACTIONS
    //-------------------------------------------------------------------------
    
    public void onNewUser() {
        state = STATE.NEW;
        setInterface();
    }
    
    public void onEditUser() {
        state = STATE.EDIT;
        setInterface();
    }
    
    public void onDeleteUser() {
        User user = userTable.getSelectionModel().getSelectedItem();
        
        boolean deletionResult = service.deleteItem(user);
        
        if ( deletionResult ) {
            showSuccessAlert("User has been deleted");
        } else {
            showErrorAlert("deletion", "Error deleting user");
        }
        
        refreshUserTable();
        
    }
    
    public void onAction() {
        
        switch ( state ) {
            case NEW:
                insertUser();
                break;
            case EDIT:
                modifyUser();
                break;
        }        
        
        refreshUserTable();
        
    }
    
    public void onSearch(ActionEvent event) {
        String param = ((TextField) event.getSource()).getText();
        //listView.getItems().setAll(service.search(choiceBox.getValue(), param));
    }

    public void onSendMail(ActionEvent event) {
        if (userTable.getSelectionModel().getSelectedItem() != null) {
            if (userTable.getSelectionModel().getSelectedItem().getEmail() != null) {
                String mail = userTable.getSelectionModel().getSelectedItem().getEmail();
                System.out.println("mailto:" + mail);
            } else {
                System.err.println("ERROR: USER with NULL mail");
            }
        } else {
            System.err.println("ERROR: NO select USER");
        }
    }
    
    private void insertUser() {
        
        User user = formFormToUser();
        
        int insertionResult = service.insertItem(user);
            
        showResultAlert(insertionResult, "insertion");
    }
    
    private void modifyUser() {

        User userNew = formFormToUser();
        userNew.setId(userTable.getSelectionModel().getSelectedItem().getId());
        
        int modificationResult = service.modifyItem(userNew);
        
        showResultAlert(modificationResult, "modification");           

        
    }
    
    private void showResultAlert( int operationResult, String operationKind ) {
        if (operationResult == UserService.NO_MATCH || operationResult == UserService.UNKNOW_ERROR) {
            showErrorAlert(operationKind, "please check al data and try again");
        } else if (operationResult == UserService.PUBLICNAME_FOUND) {
            showErrorAlert(operationKind, "\"public name\" already exists");
        } else if (operationResult == UserService.NAME_FOUND) {
            showErrorAlert(operationKind, "\"name\" already exists");
        } else if (operationResult == UserService.PHONE_FOUND) {
            showErrorAlert(operationKind, "\"phone number\" already exists");
        } else if (operationResult == UserService.EMAIL_FOUND) {
            showErrorAlert(operationKind, "\"email adress\" already exists");
        } else if (operationResult >= 1) {
            showSuccessAlert("User " + operationResult + " succesfully completed");
        }
        
    }
    
    //FORM AUXILIAR METHODS
    //-------------------------------------------------------------------------
    
    
    private void showErrorAlert(String actionRelated, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("\"User " + actionRelated +" has failed");
        alert.setContentText(message);

        alert.showAndWait();
    }
    
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
    
    private void inputDataValidation() {
        if (passConfirmationField != null && !passConfirmationField.
                getText().toString().equals(passwordField.getText().toString())) {

            showErrorAlert("edition", "\"password\" and \"confirm password\" fields does not match, please confirm password");
        } 
    }
    
    private void refreshUserTable() {
        ObservableList<User> users = FXCollections.observableArrayList(service.getAll());
        userTable.setItems(users);
    }
    
    
    
    private void fromUserToForm(User user) {
        roleBox.getSelectionModel().select(user.getUserRole());
        languageBox.getSelectionModel().select(user.getLanguage());
        nicknameField.setText(user.getNickname());
        nameField.setText(user.getName());
        phoneField.setText(user.getPhone());
        emailField.setText(user.getEmail());
        pubDescField.setText(user.getPublicDes());
        privDescField.setText(user.getPrivateDes());
    }
    
       
    private User formFormToUser() {
        User user = new User();
        
        user.setNickname(nicknameField.getText());
        user.setName(nameField.getText());
        user.setPhone(phoneField.getText());
        user.setEmail(emailField.getText());
        user.setAds(null);
        user.setPrivateDes(privDescField.getText());
        user.setPublicDes(pubDescField.getText());
        user.setUserRole(roleBox.getSelectionModel().getSelectedItem());
        user.setLanguage(languageBox.getSelectionModel().getSelectedItem());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        user.setDatePassword("2000-10-10");
        user.setPassword(passwordField.getText());
        user.setMemberSince(dateFormat.format(date));
        
        return user;
    }
    

    
    private void setInterface() {
        switch (state) {
            case READ:
                createUserButton.setDisable(true);
                resetPassButton.setDisable(true);
                // Set Interface to READ:VIEW
                for (TextInputControl tf : userTextFields) {
                    tf.setEditable(false);
                    tf.setMouseTransparent(true);
                    tf.setFocusTraversable(false);
                }
                
                break;
            case NEW:
                createUserButton.setDisable(false);
                createUserButton.setText("create user");
                for (TextInputControl tf : userTextFields) {
                    tf.toString();
                    tf.setText("");
                    tf.setEditable(true);
                    tf.setMouseTransparent(false);
                    tf.setFocusTraversable(true);
                }

                break;
            case EDIT:
                createUserButton.setDisable(false);
                createUserButton.setText("update user");
                for (TextInputControl tf : userTextFields) {
                    tf.toString();
                    tf.setEditable(true);
                    tf.setMouseTransparent(false);
                    tf.setFocusTraversable(true);
                }

                break;
        }
    }
}
