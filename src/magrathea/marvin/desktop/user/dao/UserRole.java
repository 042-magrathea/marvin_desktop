/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magrathea.marvin.desktop.user.dao;

/**
 *
 * @author boscalent
 */
public enum UserRole {
    wrong, user, editor, administrator;
    
    public static String getById(int id){
        for (UserRole userRole : UserRole.values()){
            if ( userRole.ordinal() == id ){
                return userRole.toString();
            }
        }
        return null;
    }
}