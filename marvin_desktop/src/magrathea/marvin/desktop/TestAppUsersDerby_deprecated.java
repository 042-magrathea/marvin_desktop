package magrathea.marvin.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import magrathea.marvin.desktop.user.dao.DerbyDAO.DerbyUserDAO;
import magrathea.marvin.desktop.user.dao.UserDAO;
import magrathea.marvin.desktop.user.controller.UserController_old;
import magrathea.marvin.desktop.user.dao.HTTPRequest.HTTPRequestUserDAO;

import magrathea.marvin.desktop.user.service.UserService;

/**
 * Magrathea group - Project Marvin
 * App Desktop - Only for test DAO with different provider (Derby)
 * In User CRUD
 */
@Deprecated
public class TestAppUsersDerby_deprecated extends Application {

    private UserDAO buildDAO(){
        return new DerbyUserDAO();
        // return new HTTPRequestUserDAO();
    }
    
    private UserService buildModel(){
        return new UserService(buildDAO());
    }
    
    private UserController_old buildController(Stage stage){
        return new UserController_old(buildModel(), stage);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "user/view/user_old.fxml"));
        loader.setControllerFactory(t -> buildController(stage));
        
        stage.setScene( new Scene(loader.load()));
        stage.show();
    }
       
    public static void main(String[] args) throws Exception {
        launch(args);
        
        /*
        UserDAO userDAO = new DerbyUserDAO();
        
        userDAO.setup();

        UserService userModel = new UserService(userDAO);

        userModel.addNewUser("ARNAU", "1234","arnau@magrathea.com", true);
        userModel.addNewUser("IVÁN", "2345","ivan@magrathea.com", true);
        userModel.addNewUser("VILLA", "3456","villa@magrathea.com", true);
        
        userDAO.close();
        
        System.exit(0); 
        */
    }
}