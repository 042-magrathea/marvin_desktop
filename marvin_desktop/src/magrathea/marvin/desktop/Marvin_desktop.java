package magrathea.marvin.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import magrathea.marvin.desktop.DAO.DerbyDAO.DerbyUserDAO;
import magrathea.marvin.desktop.DAO.model.UserDAO;
import magrathea.marvin.desktop.controller.UserController;

import magrathea.marvin.desktop.library.UserLibrary;

/**
 * Magrathea group
 * Project Marvin
 * App Desktop
 * @author Iván Cañizares Gómez
 */
public class Marvin_desktop extends Application {

    private UserDAO buildDAO(){
        return new DerbyUserDAO();
        // TODO: return new HTTPRequestUserDAO();
    }
    
    private UserLibrary buildModel(){
        return new UserLibrary(buildDAO());
    }
    
    private UserController buildController(Stage stage){
        return new UserController(buildModel(), stage);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "view/user.fxml"));
        loader.setControllerFactory(t -> buildController(stage));
        
        stage.setScene( new Scene(loader.load()));
        stage.show();
    }
    
    
    
    public static void main(String[] args) throws Exception {
        launch(args);
        
        /*
        UserDAO userDAO = new DerbyUserDAO();
        
        userDAO.setup();

        UserLibrary userModel = new UserLibrary(userDAO);

        userModel.addNewUser("ARNAU", "1234","arnau@magrathea.com", true);
        userModel.addNewUser("IVÁN", "2345","ivan@magrathea.com", true);
        userModel.addNewUser("VILLA", "3456","villa@magrathea.com", true);
        
        userDAO.close();
        
        System.exit(0); 
        */
    }
}