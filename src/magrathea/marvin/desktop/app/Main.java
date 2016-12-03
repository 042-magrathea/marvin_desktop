package magrathea.marvin.desktop.app;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.controller.LoginController;
import magrathea.marvin.desktop.app.controller.MainController;
import magrathea.marvin.desktop.app.controller.MainMenuBarController;
import magrathea.marvin.desktop.app.controller.ProfileController;
import magrathea.marvin.desktop.app.dao.DAO;
import magrathea.marvin.desktop.app.model.MarvinConfig;
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

    private double MINIMUM_WINDOW_WIDTH;
    private double MINIMUM_WINDOW_HEIGHT;
    private double WINDOW_WIDTH;
    private double WINDOW_HEIGHT;

    // TODO: move to preferences. Look for external preferences file
    // Study the possibility of configuration in the installation process.
    /**
     * URL point to a folder with php webservices
     */
    public static final String SERVER
            = MarvinConfig.getInstance().getProperty("SERVER_ADDRESS");

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
        // Chivato properties
        System.out.println(MarvinConfig.getInstance().getAllProperyNames());

        // Load from config properties
        MINIMUM_WINDOW_HEIGHT = Double.parseDouble(MarvinConfig.getInstance().getProperty("MINIMUM_WINDOW_HEIGHT"));
        MINIMUM_WINDOW_WIDTH = Double.parseDouble(MarvinConfig.getInstance().getProperty("MINIMUM_WINDOW_WIDTH"));
        WINDOW_HEIGHT = Double.parseDouble(MarvinConfig.getInstance().getProperty("WINDOW_HEIGHT"));
        WINDOW_WIDTH = Double.parseDouble(MarvinConfig.getInstance().getProperty("WINDOW_WIDTH"));

        try {
            stage = primaryStage;
            stage.setTitle("Marvin Login");
            stage.setWidth(MINIMUM_WINDOW_HEIGHT);
            stage.setHeight(MINIMUM_WINDOW_WIDTH);
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
        }
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
        FXMLLoader loader = new FXMLLoader();
        AnchorPane pane = (AnchorPane) loaderFXML("view/profile.fxml", loader);
        setStage(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        ProfileController profile = loader.getController();
        profile.setApp(this);        
    }

    public void gotoLogin() {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane pane = (AnchorPane) loaderFXML("view/login.fxml", loader);
        setStage(pane, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        LoginController login = loader.getController();
        login.setApp(this);
    }

    public void gotoMain() {
        try {
            // Load Menu by Controller ( we need a ref. for LoginOut)
            FXMLLoader barLoader = new FXMLLoader();
            MenuBar menuBar = (MenuBar) loaderFXML("view/mainMenuBar.fxml", barLoader);
            MainMenuBarController barController = barLoader.<MainMenuBarController>getController();
            barController.setMainController(this);
            
            // Load Center Pane by fxml we don't need the controller
            URL centerPaneURL = getClass().getResource("view/main.fxml");
            AnchorPane centerPane = FXMLLoader.load(centerPaneURL);
            
            // Load Root container by controller
            URL rootPaneURL = getClass().getResource("view/main_1.fxml");
            root = FXMLLoader.load(rootPaneURL);
            root.setTop(menuBar);
            root.setCenter(centerPane);
            
            setStage(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Aux. methods refactor for charge the controler with Main reference
     * and callback Scene loader
     * @param fxml
     * @param loader
     * @return 
     */
    private Region loaderFXML(String fxml, FXMLLoader loader) {
        Region region = null;
        InputStream in = null;
        try {
            in = Main.class.getResourceAsStream(fxml);
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getResource(fxml));
            region = (Region) loader.load(in);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return region;
    }
    
    private void setStage(Region region, double width, double height){
        Scene scene = new Scene(region, width, height);
        stage.setScene(scene);
        //stage.sizeToScene();
        stage.setWidth(width);
        stage.setHeight(width);
    }
}
