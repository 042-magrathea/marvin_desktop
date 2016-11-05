/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.tournament.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import magrathea.marvin.desktop.app.Main;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.tournament.service.TournamentService;

/**
 *
 * @author boscalent
 */
public class TournamentController {
    
    @FXML private ListView<Tournament> listTournaments;
    
    private TournamentService service = null;
    
    public TournamentController(){
        service = new TournamentService(Main.buildDAO("Tournament"));
    }
    
    // ACTIONS
    public void callTournamentsQueryFake(ActionEvent event) {
        listTournaments.getItems().setAll( service.getAll());
    }
    
}