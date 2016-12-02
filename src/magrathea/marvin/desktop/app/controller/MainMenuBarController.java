package magrathea.marvin.desktop.app.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import magrathea.marvin.desktop.app.Main;

/**
 * @author Iván Cañizares Gómez Load the panes with the FXMLLoader on every use
 * and put in the center of the root, The panes should not save state and reload
 * it to avoid get lazy On every invocation connect to DAO provider. Not ORM
 * it's implemented.
 */
public class MainMenuBarController implements Initializable {

    MainController main;

    public void setMainController(MainController main) {
        this.main = main;
    }
    
    public void loginOut(ActionEvent event) {
        //MainController main = Main.getMainController();
        main.processLogout(event);
    }

    /**
     * Event handler for MainMenu item
     */
    @FXML
    void switchToMain(ActionEvent event) {
        try {
            URL paneOneUrl = getClass()
                    .getResource("/magrathea/marvin/desktop/app/view/main.fxml");
            AnchorPane paneOne = FXMLLoader.load(paneOneUrl);
            BorderPane border = Main.getRoot();
            border.setCenter(paneOne);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for TournamentMenu item
     */
    @FXML
    void switchToTournament(ActionEvent event) {
        try {
            URL paneTwoUrl = getClass()
                    .getResource("/magrathea/marvin/desktop/tournament/view/tournament.fxml");
            AnchorPane paneTwo = FXMLLoader.load(paneTwoUrl);

            BorderPane border = Main.getRoot();
            border.setCenter(paneTwo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for UserMenu item
     */
    @FXML
    void switchToUser(ActionEvent event) {
        try {
            URL paneThreeUrl = getClass()
                    .getResource("/magrathea/marvin/desktop/user/view/user.fxml");
            AnchorPane paneThree = FXMLLoader.load(paneThreeUrl);

            BorderPane border = Main.getRoot();
            border.setCenter(paneThree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for UserMenu item
     */
    @FXML
    void switchToInsertUser(ActionEvent event) {
        try {
            URL paneFourUrl = getClass()
                    .getResource("/magrathea/marvin/desktop/user/view/insertUser.fxml");
            AnchorPane paneThree = FXMLLoader.load(paneFourUrl);

            BorderPane border = Main.getRoot();
            border.setCenter(paneThree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for UserMenu item
     */
    @FXML
    void switchToConfiguration(ActionEvent event) {
    }

    @FXML
    void switchToHost(ActionEvent event) {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Implement
    }
}
