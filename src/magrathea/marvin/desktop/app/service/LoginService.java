package magrathea.marvin.desktop.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.app.controller.LoginController;
import magrathea.marvin.desktop.app.controller.MainMenuBarController;
import magrathea.marvin.desktop.app.controller.ProfileController;
import magrathea.marvin.desktop.app.model.MarvinConfig;
import magrathea.marvin.desktop.user.model.User;
import magrathea.marvin.desktop.user.service.UserService;

/**
 *
 * @author boscalent
 */
public class LoginService {

    private static final MarvinConfig PROPS = MarvinConfig.getInstance();
    private static BorderPane root = new BorderPane();

    private Stage stage;
    private UserService userService = new UserService();

    private double MINIMUM_WINDOW_WIDTH;
    private double MINIMUM_WINDOW_HEIGHT;
    private double WINDOW_WIDTH;
    private double WINDOW_HEIGHT;

    ////////////////////// CONSTRUCTOR /////////////////////////
    private LoginService() {
        // We need ref. To root pane
        fillConfig();
    }
    
    /**
     * fill from properties
     */
    private void fillConfig() {
        MINIMUM_WINDOW_HEIGHT = Double.parseDouble(PROPS.getProperty("MINIMUM_WINDOW_HEIGHT"));
        MINIMUM_WINDOW_WIDTH = Double.parseDouble(PROPS.getProperty("MINIMUM_WINDOW_WIDTH"));
        WINDOW_HEIGHT = Double.parseDouble(PROPS.getProperty("WINDOW_HEIGHT"));
        WINDOW_WIDTH = Double.parseDouble(PROPS.getProperty("WINDOW_WIDTH"));
    }

    ////////////////////// END CONSTRUCTOR /////////////////////
    
    ////////////////////// SINGLETON ///////////////////////////
    // Bill Pugh singleton pattern
    private static class LazyHolder {

        private static final LoginService INSTANCE = new LoginService();
    }

    public static LoginService getInstance() {
        return LazyHolder.INSTANCE;
    }
    ////////////////////// END SINGLETON ////////////////////////
    
    // First point in interface
    public void initializeApp(Stage stage){
            this.stage = stage;
            stage.setTitle("Marvin Login");
            stage.setWidth(MINIMUM_WINDOW_WIDTH);
            stage.setHeight(MINIMUM_WINDOW_HEIGHT);
            gotoLogin();
            stage.show();
    }
        
    // Change Stage logic
    public void gotoLogin() {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane pane = (AnchorPane) loaderFXML("view/login.fxml", loader);
        setStage(pane, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        LoginController login = loader.getController();
        login.setApp(this);
    }

    public void gotoProfile() {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane pane = (AnchorPane) loaderFXML("view/profile.fxml", loader);
        setStage(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        ProfileController profile = loader.getController();
        profile.setApp(this);
    }

    public void gotoMain() {
        try {
            // Load Menu by Controller ( we need a ref. for LoginOut)
            FXMLLoader barLoader = new FXMLLoader();
            MenuBar menuBar = (MenuBar) loaderFXML("/magrathea/marvin/desktop/app/view/mainMenuBar.fxml", barLoader);
            MainMenuBarController barController = barLoader.<MainMenuBarController>getController();
            barController.setApp(this);

            // Load by FXML
            AnchorPane centerPane = 
                    (AnchorPane) loaderFXML("/magrathea/marvin/desktop/app/view/main.fxml", new FXMLLoader());
            
            // Load Root container by controller
            URL rootPaneURL = getClass().getResource("/magrathea/marvin/desktop/app/view/main_1.fxml");
            root = FXMLLoader.load(rootPaneURL);
            root.setTop(menuBar);
            root.setCenter(centerPane);

            setStage(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Aux. methods refactor for charge the controler with Main reference and
     * callback Scene loader
     *
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

    private void setStage(Region region, double width, double height) {
        Scene scene = new Scene(region, width, height);
        stage.setScene(scene);
        //stage.sizeToScene();
        stage.setWidth(width);
        stage.setHeight(height);
    }

    // Return root
    public static BorderPane getRoot() {
        return root;
    }
    
    //////////////// USER stuff
    // User Login
    private User loggedUser = new User();
    
    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String userId, String password) {
        loggedUser = userService.validateAuthenticator(userId, password);
        // TODO: User needs fill fields
        //if ( loggedUser.getAdds() == null ){
        //    gotoProfile();
        //}
        
        if ( loggedUser != null ) {
            gotoProfile();      // TODO: Check if user needs to fill fields
            return true;
        } else {
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }
}