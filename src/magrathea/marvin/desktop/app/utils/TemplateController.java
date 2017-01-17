package magrathea.marvin.desktop.app.utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import magrathea.marvin.desktop.app.utils.Crud;
import magrathea.marvin.desktop.app.utils.CrudState;
import magrathea.marvin.desktop.app.utils.MessageHelper;
import magrathea.marvin.desktop.user.dao.PreferedLanguage;
import magrathea.marvin.desktop.user.dao.UserRole;
import magrathea.marvin.desktop.user.model.User;
import magrathea.marvin.desktop.user.service.UserService;

/**
 * Controller for the View in fxml format.
 * @author Arnau, Iván
 */
public class TemplateController /*extends Crud*/ {
    
    /* Specials nodes of the fxml View for this concrete implementation. 
     * All common stuff for the crud View is stored in the parent class. */
    /*
    @FXML private TableView table_list_subsection;    // updates super
    @FXML private Button resetPassButton;
    @FXML private ChoiceBox<UserRole> roleBox;
    @FXML private ChoiceBox<PreferedLanguage> languageBox;
    @FXML private TextField nicknameField, nameField, phoneField, emailField;
    @FXML private PasswordField passwordField, passConfirmationField;
    @FXML private TextArea pubDescField, privDescField; */

    /* Specials vars that can’t be generalized without a pattern like an adapter 
     * a factory or reflection because needs a dynamic load of class and casting, 
     * complicated thing for a static language like Java.
     * The approach is store these vars in the concrete class that’s know about the type.
     Exemple in User:
     - private UserService service = null;
     - private ObservableList<User> items;
    */
    
    // TODO: Set concrete Service and Observable list
    //private UserService service = null;
    
    
    /**
     * Default constructor of the class for load the Service
     */
    
    
    // public TemplateController() {
        /* Load Service that coordinate access to the DAO providers. Usually, every
        class in the model has a service that know about him. Complex View that need 
        access to different models, can have a service that coordinate them.
        Exemple in User
        - this.service = new UserService();        // Exemple for new Service
        - this.service = Service.getInstance();    // Exemple for Singlenton Access */
        
        // TODO: Set Service
        /*this.service = new UserService();
    }
    */
    

    /**
     * Implements Interface Initilizable from parent class. When the controller 
     * is called from a FXMLoader, the parameters are injected.
     * @param location
     * @param resources i18n resources. Injected by LoginService in top and center
     * layout of the root BorderPane.
     */
    
    /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Update TableView<? extends Object> in Super with concrete implementation
        super.table_list_subsection = table_list_subsection;
        super.initialize(location, resources);
        
        // Charge Search Items in TableView
        setListenerToSearchField();
        refreshTable();

        //---------------------------------------------------------------------
        // SPECIAL Stuff for User
        // ENUMS
        languageBox.getItems().setAll(PreferedLanguage.values());   // ENUM values
        languageBox.getSelectionModel().selectFirst();              // select first
        roleBox.getItems().setAll(UserRole.getValuesOK());          // method in ENUM          
        roleBox.getSelectionModel().selectFirst();

        // CHECKS FORM
        checkFields = new HashMap<TextInputControl, String>();
        setFieldsCheck(nameField, MessageHelper.NAME_REGEX);
        setFieldsCheck(phoneField, MessageHelper.PHONE_REGEX);
        setFieldsCheck(emailField, MessageHelper.EMAIL_REGEX);
        setFieldsCheck(passwordField, MessageHelper.PASS_REGEX);
        setFieldsCheck(passConfirmationField, MessageHelper.PASS_REGEX);

        // Link two passwords fields
        setPasswordFieldConfirmation(passwordField, passConfirmationField);

        state = CrudState.READ;
        setInterface();
    }

    //ACTIONS
    //-------------------------------------------------------------------------
    @Override
    public void onDelete() {
        User user = (User) table_list_subsection.getSelectionModel().getSelectedItem();
        boolean deletionResult = service.deleteItem(user);
        if (deletionResult) {
            MessageHelper.showSuccessAlert(resources.getString("marvin_USER")
                    + " " + user.getNickname() + resources.getString("crud_x_deleted"));
        } else {
            MessageHelper.showErrorAlert(resources.getString("crud_delete"),
                    resources.getString("crud_delete_error") + resources.getString("marvin_USER"));
        }
        refreshTable();
    }

    @Override
    public void onCancel(ActionEvent event) {
        super.onCancel(event);
        // Works because propmt text already defined
        passwordField.setText("");
        passConfirmationField.setText("");
    }

    public void onPassReset() {
        passwordField.setText(MessageHelper.DEFAULT_PASS);
        passConfirmationField.setText(MessageHelper.DEFAULT_PASS);
    }

    public void onAction() {
        if (inputDataValidation()) {
            switch (state) {
                case NEW:
                    insert();
                    break;
                case EDIT:
                    modify();
                    break;
            }
            refreshTable();
        } else {
            switch (state) {
                case NEW:
                    MessageHelper.showErrorAlert(
                            resources.getString("crud_create"),
                            resources.getString("crud_generic_error_text"));
                    break;
                case EDIT:
                    MessageHelper.showErrorAlert(
                            resources.getString("crud_edit"),
                            resources.getString("crud_generic_error_text"));
                    break;
            }
        }
    }

    private void insert() {
        User user = fromFormToItem();
        int insertionResult = service.insertItem(user);
        showResultAlert(insertionResult, "insertion");
    }

    private void modify() {
        User userNew = fromFormToItem();
        userNew.setId(((User) (table_list_subsection.getSelectionModel().getSelectedItem())).getId());
        int modificationResult = service.modifyItem(userNew);
        showResultAlert(modificationResult, "modification");
    }

    // Messages from checks in Service Layer
    private void showResultAlert(int operationResult, String operationKind) {
        if (operationResult == UserService.NO_MATCH || operationResult == UserService.UNKNOW_ERROR) {
            MessageHelper.showErrorAlert(operationKind, resources.getString("crud_generic_error_text"));

        } else if (operationResult == UserService.PUBLICNAME_FOUND) {
            MessageHelper.showErrorAlert(operationKind, resources.getString("user_nickname")
                    + resources.getString("crud_error_exist_text"));

        } else if (operationResult == UserService.NAME_FOUND) {
            MessageHelper.showErrorAlert(operationKind, resources.getString("user_name")
                    + resources.getString("crud_error_exist_text"));

        } else if (operationResult == UserService.PHONE_FOUND) {
            MessageHelper.showErrorAlert(operationKind, resources.getString("user_phone")
                    + resources.getString("crud_error_exist_text"));

        } else if (operationResult == UserService.EMAIL_FOUND) {
            MessageHelper.showErrorAlert(operationKind, resources.getString("user_email")
                    + resources.getString("crud_error_exist_text"));

        } else if (operationResult >= 1) {
            MessageHelper.showSuccessAlert(operationKind + resources.getString("crud_operation_complete"));
        }

    }
    */

    //-------------------------------------------------------------------------
    //  FORM AUXILIAR METHODS FOR CONCRETE CLASS
    //  1 - refreshTable()
    //  2 - fromItemToForm(Object item)
    //  3 - fromFormToItem()
    //  -----
    //  4 - setRead()
    //  5 - setNew()
    //  6 - setEdit()
    //-------------------------------------------------------------------------
    /*
    @Override
    protected void refreshTable() {
        items = FXCollections.observableArrayList(service.getAll());
        super.refreshTable();
    }
    */

    /**
     * On Change the input text, the table of items is refreshed with new data.
     * This method needs to be concrete for the casting in the filters for the concrete
     * type of items.
     * For work need to connected with a TextField named searchField.
     */
    
    /*
    protected void setListenerToSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            itemsFilter.setPredicate(item -> {
                // If filter text is empty, display all data.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                // Filters
                if (((User) item).getNickname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (((User) item).getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
    }

    @Override
    protected void fromItemToForm(Object item) {
        User user = (User) item;
        roleBox.getSelectionModel().select(user.getUserRole());
        languageBox.getSelectionModel().select(user.getLanguage());
        nicknameField.setText(user.getNickname());
        nameField.setText(user.getName());
        phoneField.setText(user.getPhone());
        emailField.setText(user.getEmail());
        pubDescField.setText(user.getPublicDes());
        privDescField.setText(user.getPrivateDes());
    }

    @Override
    protected User fromFormToItem() {
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
    */

    /*
     * Special Stuff in User STATE 
     */
    
    /*
    @Override
    protected void setRead() {
        createButton.setVisible(false);
        resetPassButton.setVisible(false);
        cancelButton.setVisible(false);
        // Set Interface to READ:VIEW
        for (TextInputControl tf : textFields) {
            tf.setEditable(false);
            tf.setMouseTransparent(true);
            tf.setFocusTraversable(false);
        }
    }

    @Override
    protected void setNew() {
        cancelButton.setVisible(true);
        createButton.setVisible(true);
        createButton.setText(resources.getString("crud_create"));
        resetPassButton.setVisible(true);
        for (TextInputControl tf : textFields) {
            tf.toString();
            tf.setText("");
            tf.setEditable(true);
            tf.setMouseTransparent(false);
            tf.setFocusTraversable(true);
        }
    }

    @Override
    protected void setEdit() {
        cancelButton.setVisible(true);
        createButton.setVisible(true);
        createButton.setText(resources.getString("crud_update"));
        resetPassButton.setVisible(true);
        for (TextInputControl tf : textFields) {
            tf.toString();
            tf.setEditable(true);
            tf.setMouseTransparent(false);
            tf.setFocusTraversable(true);
        }
    } */
}
