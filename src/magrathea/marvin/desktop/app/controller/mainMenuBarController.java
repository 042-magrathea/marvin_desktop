package magrathea.marvin.desktop.app.controller;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import magrathea.marvin.desktop.app.Main;

/**
 *
 * @author boscalent Load the panes with the FXMLLoader on every use The panes
 * should not save state internally Reload it to avoid get lazy
 */
public class mainMenuBarController {

    @FXML
    private MenuItem displayOne; // Value injected by FXMLLoader
    @FXML
    private MenuItem displayTwo; // only full pane Menu
    @FXML
    private MenuItem displayThree; // only full pane Menu

    /**
     * Event handler for MenuItem one
     */
    @FXML
    void switchToOne(ActionEvent event) {
        try {
            URL paneOneUrl = getClass().getResource("/magrathea/marvin/desktop/app/view/main.fxml");
            AnchorPane paneOne = FXMLLoader.load(paneOneUrl);
            BorderPane border = Main.getRoot();
            border.setCenter(paneOne);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for MenuItem two
     */
    @FXML
    void switchToTwo(ActionEvent event) {
        try {
            URL paneTwoUrl = getClass().getResource("/magrathea/marvin/desktop/tournament/view/tournament.fxml");
            AnchorPane paneTwo = FXMLLoader.load(paneTwoUrl);

            BorderPane border = Main.getRoot();
            border.setCenter(paneTwo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Event handler for MenuItem two
     */
    @FXML
    void switchToThree(ActionEvent event) {
        try {
            URL paneThreeUrl = getClass().getResource("/magrathea/marvin/desktop/user/view/user.fxml");
            AnchorPane paneThree = FXMLLoader.load(paneThreeUrl);

            BorderPane border = Main.getRoot();
            border.setCenter(paneThree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}