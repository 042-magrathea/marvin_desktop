package magrathea.marvin.desktop.tournament.controller;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.tournament.model.Prize;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.tournament.service.TournamentService;
import magrathea.marvin.desktop.user.model.User;

/**
 *
 * @author boscalent
 */
public class TournamentController {

    @FXML
    private ListView<Tournament> listTournaments;
    @FXML
    private ListView<Prize> listPrizes;
    @FXML
    private ListView<User> listUsers;
    @FXML
    private Button runButton;
    @FXML
    private TextField tournamentInfo;

    private TournamentService service = null;

    public TournamentController() {
        service = new TournamentService(Main.buildDAO("Tournament"));
    }

    public void initialize() {

        listTournaments.getItems().setAll(service.getAll());
        listTournaments.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    fill(newValue);
                });
        runButton.setDisable(true);
    }

    public void fill(Tournament tournament) {
        List<Prize> prizes = tournament.getPrizes();
        if (prizes != null & !prizes.isEmpty()) {
            listPrizes.getItems().clear();
            listPrizes.getItems().addAll(prizes);
        } else {
            listPrizes.getItems().clear();
        }

        List<User> users = tournament.getUsers();
        boolean hasUsers = users != null & !users.isEmpty();
        if (hasUsers) {
            listUsers.getItems().clear();
            listUsers.getItems().addAll(users);
        } else {
            listUsers.getItems().clear();
        }

        checkButton(hasUsers);
    }

    public void checkButton(boolean hasUsers) {
        if (hasUsers) {
            runButton.setDisable(false);
            tournamentInfo.setText("Good Luck!");
        } else {
            runButton.setDisable(true);
            tournamentInfo.setText("No users in Tournament. Can't run.");
        }
    }
    
    /**
     * GET a FAKE result of Tournament
     * Only for prototype ( Local data, no con to Service Server )
     */
    public void runTournament() {
        Tournament tournament = listTournaments.getSelectionModel().getSelectedItem();
        
        try {
            //URL fakeResultURL = getClass()
            //        .getResource("/magrathea/marvin/desktop/tournament/view/tournamentFakeResult.fxml");
            //AnchorPane fakeResultPane = FXMLLoader.load(fakeResultURL);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( getClass()
                    .getResource("/magrathea/marvin/desktop/tournament/view/tournamentFakeResult.fxml"));
            
            
            
            Window parentWindows = Main.getRoot().getScene().getWindow();
                        
            Stage dialogStage = new Stage();
            dialogStage.setTitle( "Results of " + tournament.getName() + " tournament");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner( parentWindows );
            
            // FIXTO to auxiliar method for center in parent stage
            dialogStage.setX(
                    (( parentWindows.getWidth() - 600) /2) + parentWindows.getX());
            dialogStage.setY(
                    (( parentWindows.getHeight()- 400) /2) + parentWindows.getY());
            dialogStage.setResizable(false);
            
            //AnchorPane fakeResultPane = (AnchorPane) loader.load();
            Scene scene = new Scene((AnchorPane) loader.load());
            dialogStage.setScene(scene);
            
            // Set controller
            TournamentFakeResultController controller = loader.<TournamentFakeResultController>getController();
            controller.setTournament(tournament);
                                    
            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
