package magrathea.marvin.desktop.user.dao;

/**
 * Only contain fields that can be filter of the given class
 * TODO: PASSWORD Must be deleted
 * @author Iván Cañizares Gómez
 */
public enum UserSearchType {
    ID, NICKNAME, PASSWORD, EMAIL, ADMINISTRATOR;
}