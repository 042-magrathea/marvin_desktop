package magrathea.marvin.desktop.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import magrathea.marvin.desktop.app.service.LoginService;
import magrathea.marvin.desktop.app.service.StyleService;

/**
 * Load the panes with the FXMLLoader on every use. and put in the center of the
 * root, The panes should not save state and reload It to avoid get lazy On
 * every invocation connect to DAO provider Not ORM it's implemented Load the
 * panes (FXML address) from a layout ( style )
 *
 * @author Iván Cañizares Gómez
 */
public class RibbonBarController implements Initializable {

    private LoginService application;
    private ResourceBundle resources;
    private HashMap<String, String> layout;
    @FXML
    private HBox ribbonBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layout = (HashMap) StyleService.getInstance().getLayout();

        // i18n
        this.resources = resources;

        // Tooltip
        for (Node node : ribbonBox.getChildren()) {
            if (node instanceof ToggleButton) {
                Tooltip tt = new Tooltip(resources.getString("marvin_" + node.getId())); // TOFIX: i18n
                ((ToggleButton) node).setTooltip(tt);
            }
        }
    }

    public void setApp(LoginService application) {
        this.application = application;
    }

    /**
     * Special action. Not load in center Needs load a diferent root and do
     * stuff for the actual User login.
     *
     * @param event
     */
    public void loginOut(ActionEvent event) {
        System.gc();
        application.gotoLogin();
    }

    /**
     * Capture id from control and charge the view based on layout
     *
     * @param event onAction from button/MenuItem (can't equal cast)
     */
    @FXML
    public void changeView(ActionEvent event) {
        String idControlOnAction;
        // ItemView extends Object, can't be cast in control/node...
        if (event.getSource() instanceof MenuItem) {
            idControlOnAction = ((MenuItem) event.getSource()).getId();
        } else {
            idControlOnAction = ((Node) event.getSource()).getId();
        }
        String viewLoadFromLayout = layout.get(idControlOnAction);
        switchTo(viewLoadFromLayout);
    }

    /**
     * Prepare/Load an Anchor Pane in center of root (BorderPane Layout)
     *
     * @param fxml View load for these option
     */
    private void switchTo(String fxml) {
        //URL paneURL = getClass().getResource(fxml);
        //AnchorPane pane = FXMLLoader.load(paneURL);
        //pane.getStylesheets().clear();
        //pane.getStylesheets().add(application.getCSS());
        FXMLLoader centerPaneLoader = new FXMLLoader();
        AnchorPane centerPane = (AnchorPane) LoginService.getInstance()
                .loaderFXML(fxml, centerPaneLoader);    // Aux. LoginService
        centerPane.getStylesheets().clear();
        centerPane.getStylesheets().add(application.getCSS());

        LoginService.getRoot().setCenter(centerPane);

    }

    // TODO: Ribbon Bar
    // JavaDoc & Test
    // CASE: Administrator -> show Adv. Config, Others hide from Menú.
    //      This can do here or in Service (adv) for load a special Ribbon
    // Review loginOut for stuff (actual login user = null ...)
}