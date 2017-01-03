package magrathea.marvin.desktop.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.app.controller.LoginUserController;
import magrathea.marvin.desktop.app.controller.RibbonBarController;
import magrathea.marvin.desktop.app.controller.LoginProfileController;
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

    private Stage mainStage;
    private UserService userService = new UserService();

    private double MINIMUM_WINDOW_WIDTH;
    private double MINIMUM_WINDOW_HEIGHT;
    private double WINDOW_WIDTH;
    private double WINDOW_HEIGHT;

    private Locale locale;
    private ResourceBundle bundle;
    
    private String css;

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
        
        locale = new Locale(PROPS.getProperty("LANG"));
        bundle = ResourceBundle.getBundle("magrathea/marvin/desktop/app/view/i18n/marvin_i18n" , locale);
        
        css =  String.format("/magrathea/marvin/desktop/app/view/css/%s.css", PROPS.getProperty("STYLE"));
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
    public void initializeApp(Stage stage) {
        mainStage = stage;
        mainStage.setTitle(bundle.getString("login_windows_title_text"));
        mainStage.setResizable(false);
        mainStage.show();
        gotoLogin();
    }

    // Change Stage logic
    public void gotoLogin() {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane pane = (AnchorPane) loaderFXML("view/login_user.fxml", loader);
        setStage(pane, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        LoginUserController login = loader.getController();
        login.setApp(this);
    }

    public void gotoProfile() {
        FXMLLoader loader = new FXMLLoader();
        AnchorPane pane = (AnchorPane) loaderFXML("view/login_profile.fxml", loader);
        mainStage.setTitle(getLoggedUser().getNickname() + " profile");
        setStage(pane, MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        LoginProfileController profile = loader.getController();
        profile.setApp(this);
    }

    public void gotoMain() {
        try {
            // Load Menu by Controller ( we need a ref. for LoginOut)
            FXMLLoader barLoader = new FXMLLoader();
            AnchorPane menuBar = (AnchorPane) loaderFXML("/magrathea/marvin/desktop/app/view/main_ribbon_bar.fxml", barLoader);
            RibbonBarController barController = barLoader.<RibbonBarController>getController();
            barController.setApp(this);

            // Load by FXML
            AnchorPane bottomPane
                    = (AnchorPane) loaderFXML("/magrathea/marvin/desktop/app/view/main_tab_home.fxml", new FXMLLoader());

            // Load Root container by controller
            URL rootPaneURL = getClass().getResource("/magrathea/marvin/desktop/app/view/main_root_container.fxml");
            root = FXMLLoader.load(rootPaneURL);
            root.setTop(menuBar);
            root.setCenter(bottomPane);

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
            loader.setResources(bundle);
            region = (Region) loader.load(in);
            // Assign CSS
            region.getStylesheets().clear();
            region.getStylesheets().add( css );
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
        mainStage.setScene(scene);
        mainStage.setWidth(width);
        mainStage.setHeight(height);
        //mainStage.sizeToScene();
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

        if (loggedUser != null) {
            //gotoProfile();      // TODO: Check if user needs to fill fields
            return true;
        } else {
            return false;
        }
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }
    
    /** CSS
     * @return  css path*/
    public String getCSS(){
        return this.css;
    }
}
