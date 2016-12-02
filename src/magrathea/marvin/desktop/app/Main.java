package magrathea.marvin.desktop.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.controller.LoginController;
import magrathea.marvin.desktop.app.controller.MainController;
import magrathea.marvin.desktop.app.controller.MainMenuBarController;
import magrathea.marvin.desktop.app.controller.ProfileController;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.app.security.Authenticator;
import magrathea.marvin.desktop.tournament.DAO.HTTPRequest.HTTPRequestTournamentDAO;
import magrathea.marvin.desktop.user.dao.DerbyDAO.DerbyUserDAO;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.model.User;

/**
 * Desktop App starting point
 *
 * @author Iván Cañizares Gómez
 */
public class Main extends Application {

    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;
    
    // TODO: move to preferences. Look for external preferences file
    // Study the possibility of configuration in the installation process.
    /**
     * URL point to a folder with php webservices
     */
    public static final String SERVER
            = "http://marvin-server.duckdns.org/";

    // TODO: move to Aux. graphic class. Candidate to Singleton.
    /**
     * Creating a static root to pass to the controller this JavaFX pane change
     * for different components It's guided by the mainMenuApp
     */
    private static BorderPane root = new BorderPane();

    private Stage stage;
    private Scene loginScene;
    private User loggedUser;
        
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
     *
     * @param <T>
     * @param type // ENUM of DAO
     * @return concrete DAO
     */
    @SuppressWarnings("unchecked")
    public static <T extends DAO> T buildDAO(String type) {
        DAO dao = null;

        switch (type) {
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
        return (T) dao;
    }

    // TODO: Make a factory with all resource data
    // TODO: Extract constants like app size
    /**
     * Starting point of the application. Load two FXML resources. One for the
     * mainMenuBar, another for the first root pane. In this case main.fxml, the
     * main menu screen
     *
     * @see desktop screen map for reference
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            stage = primaryStage;
            stage.setTitle("Marvin Login");
            stage.setWidth(390);
            stage.setHeight(500);
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            //
        }
        /* OLD CODE FROM START
        
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
        
        */

    }

    /**
     * Only for a IDE that can't throw a JavaFX App directly (start), Netbeans
     * don't need this
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    // User Login
        public User getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String userId, String password) {
        if (Authenticator.validate(userId, password)) {
            //loggedUser = User.of(userId);
            loggedUser = Authenticator.getLoginUser();
            gotoProfile();
            return true;
        } else {
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }
    
    // Change Stage logic
    public void gotoProfile() {
        String fxml = "view/profile.fxml";
        FXMLLoader loader;
        try {
            loader = new FXMLLoader();
            InputStream in = Main.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource(fxml));
            AnchorPane page;
            try {
                page = (AnchorPane) loader.load(in);

            } finally {
                in.close();
            }
            loginScene = new Scene(page, 1024, 768);
            stage.setScene(loginScene);
            stage.sizeToScene();

            ProfileController profile = loader.getController();
            profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gotoLogin() {
        String fxml = "view/login.fxml";
        FXMLLoader loader;
        try {
            loader = new FXMLLoader();
            InputStream in = Main.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource(fxml));
            AnchorPane page;
            try {
                page = (AnchorPane) loader.load(in);
            } finally {
                in.close();
            }
            //Scene scene = new Scene(page, 390, 500);
            loginScene = new Scene(page, 390, 500);
            stage.setScene(loginScene);
            //stage.sizeToScene();
            stage.setWidth(MINIMUM_WINDOW_WIDTH);
            stage.setHeight(MINIMUM_WINDOW_HEIGHT);

            LoginController login = loader.getController();
            login.setApp(this);

        } catch (Exception ex) {
            //
        }
    }

    public void gotoMain() {
        String fxml = "view/main_1.fxml";
        FXMLLoader loader;
        try {
            // Load by controller. Later we need the for pass the controller
            // of Root (BorderPane parent). We need the for call the parent's
            // method logout() who calls to the Main store in root.
            FXMLLoader load = new FXMLLoader(getClass().getResource("view/mainMenuBar.fxml"));
            MenuBar bar = (MenuBar) load.load();
            MainMenuBarController control = load.<MainMenuBarController>getController();
            
            // Load by fxml we don't need the controller
            URL centerPaneURL = getClass().getResource("view/main.fxml");
            AnchorPane centerPane = FXMLLoader.load(centerPaneURL);
            
            // Load by controller of root borderPane.
            // We store root in static var. Later we call from menuBar
            // For change nodes in central pane.
            loader = new FXMLLoader();
            InputStream in = Main.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource(fxml));
            
            // Charge BorderPane root and assign panes
            try {
                root = (BorderPane) loader.load(in);
            } finally {
                in.close();
            }
            root.setTop(bar);
            root.setCenter(centerPane);

            
            // Make root Scene
            loginScene = new Scene(root, 1024, 768);
            stage.setScene(loginScene);
            stage.sizeToScene();
            stage.setResizable(false);
            
            MainController controller = (MainController) loader.getController();
            controller.setApp(this);
            // Pass the root controler to bar pane
            control.setMainController(controller);

        } catch (Exception ex) {
            //
        }
    }

}