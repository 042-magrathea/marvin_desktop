package magrathea.marvin.desktop.tournament.model;

/**
 * Only contain fields that can be filter of the given class
 * STATE is referred by another ENUM (TournamentSearchType.java)
 * @author Iván Cañizares Gómez
 * see Use diagram of Tournament state v2
 */
public enum TournamentStateType {
    CREATED, PUBLISHED, CLOSED, BEGINNED, FINISHED, CANCELLED, INTERRUPTED;
}