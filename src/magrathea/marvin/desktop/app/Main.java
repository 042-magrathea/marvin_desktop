package magrathea.marvin.desktop.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import magrathea.marvin.desktop.app.service.LoginService;

/**
 * Desktop App starting point
 *
 * @author Iván Cañizares Gómez
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        LoginService.getInstance().initializeApp(primaryStage);
    }

    // Only for a IDE that can't throw a JavaFX App directly (start), Netbeans don't need this
    public static void main(String[] args) {
        launch(args);
    }
}
