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
 * Desktop App starting point
 * @author Iván Cañizares Gómez
 */
public class Main extends Application {
    // TODO: move to preferences. Look for external preferences file
    // Study the possibility of configuration in the installation process.
    /**
     * URL point to a folder with php webservices 
     */     
    public static final String SERVER = 
            "http://marvin-server.duckdns.org/";
    
    // TODO: move to Aux. graphic class. Candidate to Singleton.
    /**
     * Creating a static root to pass to the controller
     * this JavaFX pane change for different components
     * It's guided by the mainMenuApp
     */
    private static BorderPane root = new BorderPane();

    // TODO: move to Aux. graphic class (with BorderPane root)
    /**
     * Just a root getter for use by a controller
     */     
    public static BorderPane getRoot() {
        return root;
    }
    
    // TODO: Refactor code to a concurrent Singleton
    // Probably needs a Factory and instanced like 
    // buidDAO.getConcreteDAOImplementation()
    /**
     * Generic DAO provider of concrete class implementation
     * @param <T>
     * @param type // ENUM of DAO
     * @return concrete DAO
     */ 
    @SuppressWarnings("unchecked")
    public static <T extends DAO> T buildDAO(String type){
        DAO dao = null;
        
        switch(type){
            case "User":                                // select one
                //dao = new DerbyUserDAO();             // Only for User test
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

    // TODO: Make a factory with all resource data
    // TODO: Extract constants like app size
    /**
     * Starting point of the application. Load two FXML resources.
     * One for the mainMenuBar, another for the first root pane. In this case
     * main.fxml, the main menu screen
     * @see desktop screen map for reference
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        // Loading FXML resources 
        URL menuBarUrl = getClass().getResource("view/mainMenuBar.fxml");
        MenuBar bar = FXMLLoader.load(menuBarUrl);

        URL centerPaneURL = getClass().getResource("view/main.fxml");
        AnchorPane centerPane = FXMLLoader.load(centerPaneURL);

        // Constructing our scene using the static root
        root.setTop(bar);
        root.setCenter(centerPane);
        Scene scene = new Scene(root, 1024, 768);        // fixed size for prototype
        scene.getStylesheets()
                .add(getClass().getResource("view/marvin.css")  // not in prototype
                        .toExternalForm());
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    /**
     * Only for a IDE that can't throw a JavaFX App directly (start), 
     * Netbeans don't need this
     * @param args 
     */
    public static void main(String[] args) {
        launch(args);
    }
}