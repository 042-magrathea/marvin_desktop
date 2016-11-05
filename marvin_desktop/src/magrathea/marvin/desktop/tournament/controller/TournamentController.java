/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.tournament.controller;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    }

    // TODO: 
    public void fill(Tournament tournament) {
        List<Prize> prizes = tournament.getPrizes();
        if (prizes != null) {
            listPrizes.getItems().clear();
            listPrizes.getItems().addAll(prizes);
        } else {
            listPrizes.getItems().clear();
        }
        
        List<User> users = tournament.getUsers();
        if ( users != null ){
            listUsers.getItems().clear();
            listUsers.getItems().addAll(users);
        } else {
            listUsers.getItems().clear();
        }
    }

}
