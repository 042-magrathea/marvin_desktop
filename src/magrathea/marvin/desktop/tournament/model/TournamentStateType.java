package magrathea.marvin.desktop.tournament.model;

import java.util.Random;

/**
 * Only contain fields that can be filter of the given class
 * STATE is referred by another ENUM (TournamentSearchType.java)
 * @author Iván Cañizares Gómez
 * see Use diagram of Tournament state v2
 */
public enum TournamentStateType {
    CREATED, PUBLISHED, CLOSED, BEGINNED, INTERRUPTED, CANCELLED, FINISHED;
    
    public static TournamentStateType randomState(){
        Random random = new Random();
        int states = TournamentStateType.values().length;
        int randomState = random.nextInt(states);
        
        return TournamentStateType.values()[randomState];
    }
}