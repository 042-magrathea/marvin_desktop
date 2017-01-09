package magrathea.marvin.desktop.app.utils;

import java.net.URL;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import magrathea.marvin.desktop.app.service.LoginService;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public abstract class Crud implements Initializable {

    @FXML protected AnchorPane root_pane;
    @FXML protected VBox extraFields;
    @FXML protected HBox table_crud_buttons;
    @FXML protected GridPane form_grid;

    protected TableView<? extends Object> table_list_subsection;    // update by concrete
    protected CrudState state;
    protected List<TextInputControl> textFields;
    protected Map<TextInputControl, String> checkFields;
    protected ResourceBundle resources;

    public Crud() {
        // For now, bundle is injected in view (fxml) but need a ref in controller
        // from 'static' source (singleton).
        this.resources = LoginService.getInstance().getBundle();
    }

    /**
     * JavaFX Like Constructor //
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Override in concrete
        setArrayTextFields();
        setCrudTooltip();
        setColumnsFromTable();
        configureTable();
    }

    public void configureTable() {
        table_list_subsection.setEditable(false);
        refreshTable();
        table_list_subsection.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);    // no bar

        //LISTENER
        table_list_subsection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (table_list_subsection.getSelectionModel().getSelectedItem() != null) {
                    Object itemSelected = table_list_subsection.getSelectionModel().getSelectedItem();
                    fromItemToForm(itemSelected);
                    state = CrudState.READ;
                    setInterface();
                }
            }
        });

        table_list_subsection.getSelectionModel().selectFirst();
    }

    protected abstract void fromItemToForm(Object item);

    protected abstract Object fromFormToItem();

    /* STATE of the CRUD stuff */
    protected void setInterface() {
        switch (state) {
            case READ:
                setRead();
                break;
            case NEW:
                setNew();
                break;
            case EDIT:
                setEdit();
                break;
        }
    }

    // ACTIONS
    public void onEdit() {
        state = CrudState.EDIT;
        setInterface();
    }

    public void onNew() {
        state = CrudState.NEW;
        setInterface();
    }

    public void onDelete() {
    }

    public void onSendMail(ActionEvent event) {
        if (table_list_subsection.getSelectionModel().getSelectedItem() != null) {
            if (((User) table_list_subsection.getSelectionModel().getSelectedItem()).getEmail() != null) {
                String mail = ((User) table_list_subsection.getSelectionModel().getSelectedItem()).getEmail();
                String nickname = ((User) table_list_subsection.getSelectionModel().getSelectedItem()).getNickname();
                MessageHelper.showFakeEmailSender(null, null, nickname, mail);
                //System.out.println("mailto:" + mail);
            } else {
                System.err.println("ERROR: USER with NULL mail");
            }
        } else {
            System.err.println("ERROR: NO select USER");
        }
    }

    // STATE
    protected abstract void setRead();

    protected abstract void setNew();

    protected abstract void setEdit();

    // MODEL
    /**  Very expensive query to server, OK for this project.
    *    SearchField & String is a trick for remember filter on refreshTable   */
    protected abstract void refreshTable();

    public void onCancel(ActionEvent event) {
        refreshTable();
        for (TextInputControl tf : textFields) {
            tf.setStyle(null);
        }
    }

    protected EventHandler makeFieldCheckerHandler(String regex, TextField field) {
        EventHandler<KeyEvent> handler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String input = field.getText();
                if (!input.matches(regex)) {
                    field.setStyle("-fx-background-color: #ff8777;");
                } else {
                    field.setStyle(null);
                }
            }
        };
        return handler;
    }

    protected void setCrudTooltip() {
        // Tooltip
        for (Node node : table_crud_buttons.getChildren()) {
            if (node instanceof Button) {
                Tooltip tt = new Tooltip(resources.getString(node.getId()));
                ((Button) node).setTooltip(tt);
            }
        }
    }

    protected void setArrayTextFields() {
        textFields = new ArrayList<>();

        // Add all instances of TextInputControl in a List for iterate later
        for (Node node : form_grid.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
            } else if (node instanceof PasswordField) {
                textFields.add((PasswordField) node);
            } else if ( node instanceof TextArea){
                textFields.add((TextArea) node);
            }
        }
        // Only if exist
        if (extraFields != null) {
            for (Node node : extraFields.getChildren()) {
                if (node instanceof TextArea) {
                    textFields.add((TextArea) node);
                }
            }
        }
    }

    protected void setColumnsFromTable() {
        for (TableColumn column : table_list_subsection.getColumns()) {
            column.setCellValueFactory(new PropertyValueFactory<>(column.getId()));
        }
    }

    public void setFieldsCheck(TextField tf, String check) {
        checkFields.put(tf, check);
        tf.setOnKeyReleased(makeFieldCheckerHandler(check, tf));
    }

    protected boolean inputDataValidation() {
        boolean check = true;

        for (TextInputControl item : checkFields.keySet()) {
            check = check && item.getText().matches(checkFields.get(item));
        }
        return check;
    }

    protected boolean passwordFieldsValidation(PasswordField passwordField, PasswordField passwordFieldConfirmation) {
        if (passwordFieldConfirmation != null
                && !passwordFieldConfirmation.getText().equals(passwordField.getText())) {
            return false;
        } else if (passwordFieldConfirmation == null) {
            return false;       // ?
        } else {
            return true;
        }
    }

    protected void setPasswordFieldConfirmation(PasswordField passwordField, PasswordField passwordFieldConfirmation) {
        passwordFieldConfirmation.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    if (!passwordFieldsValidation(passwordField, passwordFieldConfirmation)) {
                        MessageHelper.showErrorAlert(resources.getString("message_edition_title"),
                                resources.getString("crud_error_password_check"));
                    }
                }
            }
        });
    }
}
