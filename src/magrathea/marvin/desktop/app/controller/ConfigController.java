/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app.controller;

import com.sun.javafx.property.adapter.PropertyDescriptor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magrathea.marvin.desktop.app.model.MarvinConfig;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class ConfigController implements Initializable {

    private static final MarvinConfig PROPS = MarvinConfig.getInstance();
    
    @FXML
    private GridPane grid;
    
    @FXML Button saveProps;

    private boolean propsToSave = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grid.add(new Label("Property"), 0, 0);
        grid.add(new Label("Value"), 1, 0);

        Label label;
        TextField textfield;

        String[] propertyNames
                = PROPS.getAllProperyNames().toArray(
                        new String[PROPS.getAllProperyNames().size()]);
        for (int i = 0; i < propertyNames.length; i++) {
            label = new Label(propertyNames[i]);
            textfield = new TextField();
            textfield.setText(PROPS.getProperty(propertyNames[i]));
            textfield.setId(propertyNames[i]);
            // add a Listener for saving props modification
            textfield.textProperty().addListener(new TextFieldListener(textfield));
            grid.addRow(i + 1, label, textfield);
        }
    }

    // Private inner class for capture the ID of textfield
    private class TextFieldListener implements ChangeListener<String> {

        private final TextField textField;
        public TextFieldListener(TextField textField) {
            this.textField = textField;
        }
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            PROPS.setProperty(textField.getId(), newValue);
            if (propsToSave == false) {
                propsToSave = !propsToSave;
                saveProps.setDisable(!propsToSave);
            }
        }

    }
    
    public void saveProperties(){
        //PROPS.storeProps();
        
        propsToSave = false;
        saveProps.setDisable(true);
    }
    
    // TODO ConfigController
    // Change boolean stuff in buttons for observableBoolean (binding)

}
