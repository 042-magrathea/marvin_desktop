/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.app;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.tournament.DAO.HTTPRequest.HTTPRequestTournamentDAO;
import magrathea.marvin.desktop.user.dao.DerbyDAO.DerbyUserDAO;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;

/**
 *
 * @author boscalent
 */
public class Main extends Application {

    // SERVER (TODO: move to preferences)
    public static final String SERVER = 
            "http://192.168.1.123/magrathea/marvin_server-master/";
    
    // Creating a static root to pass to the controller
    private static BorderPane root = new BorderPane();

    // Just a root getter for the controller to use
    public static BorderPane getRoot() {
        return root;
    }
    
    // TODO: Refactor code to concurrent Singleton
    // for return only a DAO for type.
    @SuppressWarnings("unchecked")
    public static <T extends DAO> T buildDAO(String type){
        DAO dao = null;
        
        switch(type){
            case "User": 
                //dao = new DerbyUserDAO();
                dao = new HTTPRequestUserDAO();
                break;
            case "Tournament":
                dao = new HTTPRequestTournamentDAO();
                break;
            default:
                System.err.println("DAO NOT IMPLEMENTED");
        }
        return (T)dao;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // loading FXML resources

        URL menuBarUrl = getClass().getResource("view/mainMenuBar.fxml");
        MenuBar bar = FXMLLoader.load(menuBarUrl);

        URL paneOneUrl = getClass().getResource("view/main.fxml");
        AnchorPane paneOne = FXMLLoader.load(paneOneUrl);

        // constructing our scene using the static root
        root.setTop(bar);
        root.setCenter(paneOne);

        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets()
                .add(getClass().getResource("view/marvin.css")
                        .toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
    launch(args);
  }
}