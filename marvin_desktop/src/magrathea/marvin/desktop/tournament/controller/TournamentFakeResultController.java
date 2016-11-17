package magrathea.marvin.desktop.tournament.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import magrathea.marvin.desktop.tournament.model.Prize;
import magrathea.marvin.desktop.tournament.model.Tournament;
import magrathea.marvin.desktop.user.model.User;

/**
 * TODO: !DESTROY !Only for prototype
 * Modal Windows with fake result and prize assignment
 * @author Iván Cañizares Gómez
 */
public class TournamentFakeResultController {
    
    @FXML
    ListView<String> listFake = null;
    
    public TournamentFakeResultController() {}
    
    public void initialize(){
    }
    
    /**
     * Call by the controller of the parent Scene for pass the param
     * and do the stuff
     * @param tournament 
     */
    public void setTournament(Tournament tournament){        
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
