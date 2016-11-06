/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.tournament.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
public class TournamentFakeResultController {
    private TournamentService service = null;
    private Tournament tournament;
    
    @FXML
    ListView<String> listFake = null;
    
    // REFACTOR TO SINGLETON
    public TournamentFakeResultController() {
        service = new TournamentService( Main.buildDAO("Tournament") );
    }
    
    public void initialize(){
    }
    
    public void setTournament(Tournament tournament){
        this.tournament = tournament;
        
        //listFake.getItems().addAll(tournament.getUsers());
        List<User> users = tournament.getUsers();
        List<Prize> prizes = tournament.getPrizes();
        Collections.shuffle(users);     // Random order
        List<String> fakeResult = new ArrayList<>();
        for (int i = 0; i < users.size() ; i++ ){
            fakeResult.add("#" + (i+1) + " - (" + users.get(i).getId() + ") "
                    + users.get(i).getNickname());
        }
        
        int h = 0;
        for (int j = prizes.size() -1 ; j >= 0 ; j--){
            Prize p = prizes.get(j);
            fakeResult.set(h,fakeResult.get(h) + " - Prize! (" + p.getName() + ")");
            h++;
        }
        
        listFake.getItems().addAll(fakeResult);
    }
}
