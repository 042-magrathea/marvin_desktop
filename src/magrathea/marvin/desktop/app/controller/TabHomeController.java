package magrathea.marvin.desktop.app.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import magrathea.marvin.desktop.app.service.LoginService;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.tournament.model.TournamentInfo;
import magrathea.marvin.desktop.tournament.service.TournamentService;
import magrathea.marvin.desktop.user.model.User;

/**
 * FXML Controller class
 *
 * @author boscalent
 */
public class TabHomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML ListView<Pane> listTournament;
    @FXML TextArea usersWebView, actualInfo, generalInfo;
    LoginService application;
    TournamentService tournamentService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        tournamentService = new TournamentService();
        
        String[] infoContentText = tournamentService.getInfoContent();
        
        actualInfo.setText(infoContentText[0]);
        generalInfo.setText(infoContentText[1]);
        
        List<Tournament> tm = tournamentService.getAll();
        for (Tournament tour : tm) {
            TournamentInfo info = tournamentService.getTournamentInfo(tour.getId());

            Pane paneCell = new Pane();
            HBox hboxCell = new HBox();
            VBox vboxCellImage = new VBox();
            VBox vboxCellTournament = new VBox();
            VBox vboxCelldata = new VBox();

            paneCell.getStyleClass().add("paneCell");
            hboxCell.getStyleClass().add("hboxCell");
            vboxCellImage.getStyleClass().add("vboxCellImage");
            vboxCelldata.getStyleClass().add("vboxCellData");

            Label tournamentName = new Label(tour.getName());
            Label tournamentPublic = new Label(tour.getPublicDes());
            Label prizesNumber = new Label("Prizes: " + info.getPrizes().size());
            Label status = new Label(tour.getTournamentState().name());
            status.getStyleClass().add("label-bright");
            ImageView image = new ImageView();
            image.setFitWidth(100);
            image.setFitHeight(70);
            image.setPreserveRatio(true);
            
            image.setImage(new Image(tour.getImage(), true));
            listTournament.getItems().add(paneCell);

            vboxCellImage.getChildren().addAll(image);
            vboxCellTournament.getChildren().addAll(tournamentName, tournamentPublic, status);
            vboxCelldata.getChildren().addAll(prizesNumber);
            hboxCell.getChildren().addAll(vboxCellImage, vboxCellTournament, vboxCelldata);
            
            paneCell.getChildren().add(hboxCell);
            
            paneCell.setOnMouseClicked( (event)-> {
                setUsers(info);
            });
        }
    }
    
    private void setUsers(TournamentInfo info){
            StringBuilder content = new StringBuilder();
            for ( User u : info.getUsers()){
                content.append(u.getId());
                content.append(" - ");
                content.append(u.getNickname());
                content.append("\n");                
            }
            usersWebView.setText(content.toString());
    }

    public void setApp(LoginService application) {
        this.application = application;
    }

}
